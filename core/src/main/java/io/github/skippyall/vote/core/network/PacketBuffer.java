package io.github.skippyall.vote.core.network;

import io.github.skippyall.vote.core.storage.Storages;
import io.github.skippyall.vote.core.user.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.UUID;

public class PacketBuffer {
    ArrayList<Byte> buffer = new ArrayList<>();

    private void write(byte b) {
        buffer.add(b);
    }

    public byte next() {
        byte next = buffer.get(0);
        skip(1);
        return next;
    }

    public byte[] next(int next) {
        byte[] b = new byte[next];
        for (int i = 0; i < next; i++) {
            b[i] = next();
        }
        return b;
    }

    public long nextLong() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        for (int i = 0; i < 8; i++) {
            buffer.put(next());
        }
        return buffer.getLong(0);
    }

    public int nextInt() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        for (int i = 0; i < 4; i++) {
            buffer.put(next());
        }
        return buffer.getInt(0);
    }

    public short nextShort() {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        for (int i = 0; i < 2; i++) {
            buffer.put(next());
        }
        return buffer.getShort(0);
    }

    public int nextVarInt() {
        int value = 0;
        int position = 0;
        int currentByte;

        while (true) {
            currentByte = next() & 0xFF;
            value |= (currentByte & 0x7F) << position;

            if ((currentByte & 0x80) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public long nextVarLong() {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = next();
            value |= (long) (currentByte & 0x7F) << position;

            if ((currentByte & 0x80) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public String nextString() {
        int lenght = nextVarInt();
        return new String(next(lenght));
    }

    public byte[] nextByteArray() {
        int lenght = nextVarInt();
        //System.out.println(lenght);
        byte[] barray = new byte[lenght];
        for (int i = 0; i < lenght; i++) {
            barray[i] = next();
        }
        return barray;
    }

    public byte[] nextByteArray(int max) {
        int lenght = nextVarInt();
        if (lenght > max) {
            return null;
        }
        byte[] barray = new byte[lenght];
        for (int i = 0; i < lenght; i++) {
            barray[i] = next();
        }
        return barray;
    }

    public boolean nextBoolean() {
        return next() == 0x01;
    }

    public UUID nextUUID() {
        long msb = nextLong();
        long lsb = nextLong();
        return new UUID(msb, lsb);
    }

    public double nextDouble() {
        return Double.longBitsToDouble(nextLong());
    }

    public float nextFloat() {
        return Float.intBitsToFloat(nextInt());
    }

    public User nextUser() {
        return Storages.USER_STORAGE.getUser(nextLong());
    }

    public void skip(int skip) {
        for (int i = 0; i < skip; i++) {
            buffer.remove(0);
        }
    }

    public void writeAll(OutputStream out) {
        buffer.forEach(e -> {
            try {
                out.write(e);
            } catch (IOException e1) {
//				e1.printStackTrace();
            }
        });
        buffer.clear();
    }

    public byte[] getAll() {
        return toPrimitives(buffer.toArray(new Byte[buffer.size()]));
    }

    public int currentSize() {
        return buffer.size();
    }

    byte[] toPrimitives(Byte[] oBytes)
    {
        byte[] bytes = new byte[oBytes.length];

        for(int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }

        return bytes;
    }

    public static class PacketBufferWriter {
        private PacketBuffer buffer;
        public PacketBufferWriter(PacketBuffer buffer) {
            this.buffer = buffer;
        }

        public void write(byte b) {
            buffer.write(b);
        }

        public void writeShort(short s) {
            ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.putShort(s);
            for (int i = 0; i < 2; i++) {
                write(buffer.get());
            }
        }

        public void writeInt(int int0) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.putInt(int0);
            buffer.position(0);
            for (int i = 0; i < 4; i++) {
                write(buffer.get());
            }
        }

        public void writeLong(long int0) {
            ByteBuffer buffer = ByteBuffer.allocate(8);
            buffer.putLong(int0);
            buffer.position(0);
            for (int i = 0; i < 8; i++) {
                write(buffer.get());
            }
        }

        public void writeVarInt(int value) {
            while (true) {
                if ((value & ~0x7F) == 0) {
                    write((byte) value);
                    return;
                }

                write((byte) ((value & 0x7F) | 0x80));

                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
            }
        }

        public void writeVarLong(long value) {
            while (true) {
                if ((value & ~((long) 0x7F)) == 0) {
                    write((byte) value);
                    return;
                }

                write((byte) ((value & 0x7F) | 0x80));

                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
            }
        }

        public void writeString(String s) {
            writeByteArrayWithLength(s.getBytes());
        }

        public void writeByteArray(byte[] b) {
            for(byte b0:b) {
                //System.out.println("Write byte: " + (char)b0);
                write(b0);
            }
        }

        public void writeByteArrayWithLength(byte[] b) {
            writeVarInt(b.length);
            writeByteArray(b);
        }

        public void writeUUID(UUID uuid) {
            writeLong(uuid.getMostSignificantBits());
            writeLong(uuid.getLeastSignificantBits());
        }

        public void writeBoolean(boolean b) {
            write((byte) (b ? 0x01 : 0x00));
        }

        public void writeFloat(float flyingSpeed) {
            writeInt(Float.floatToRawIntBits(flyingSpeed));
        }

        public void writeDouble(double d) {
            writeLong(Double.doubleToRawLongBits(d));
        }

        public void writeUser(User user) {
            writeLong(user.getId());
        }
    }
}

