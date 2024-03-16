package io.github.skippyall.vote.core.network;

import java.util.ArrayList;
import java.util.function.Supplier;

public class VarInt{
    int value = 0;
    int position = 0;
    public boolean next(int next) {
        value |= (next & 0x7F) << position;

        if ((next & 0x80) == 0) return true;

        position += 7;

        if (position >= 32) throw new RuntimeException("VarInt is too big");
        return false;
    }
    public Integer getValue() {
        return value;
    }

    public static byte[] writeVarInt(int value) {
        ArrayList<Byte> bytes = new ArrayList<>();
        while (true) {
            if ((value & ~0x7F) == 0) {
                bytes.add((byte)value);
                return toByte(bytes);
            }

            bytes.add((byte)((value & 0x7F) | 0x80));

            value >>>= 7;
        }
    }

    private static byte[] toByte(ArrayList<Byte> bytes) {
        byte[] bytes0 = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            bytes0[i] = bytes.get(i);
        }
        return bytes0;
    }}
