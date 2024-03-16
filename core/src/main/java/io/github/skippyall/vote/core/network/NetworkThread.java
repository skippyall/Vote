package io.github.skippyall.vote.core.network;

import io.github.skippyall.vote.core.network.handlers.ClientNetworkHandler;
import io.github.skippyall.vote.core.network.handlers.NetworkHandler;
import io.github.skippyall.vote.core.network.handlers.ServerNetworkHandler;
import io.github.skippyall.vote.core.network.packets.AnswerPacket;
import io.github.skippyall.vote.core.network.packets.Packet;
import io.github.skippyall.vote.core.network.packets.VoteCreatePacket;
import io.github.skippyall.vote.core.network.tasks.Task;
import io.github.skippyall.vote.core.storage.Storages;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class NetworkThread extends Thread{
    private static final List<Function<PacketBuffer, Packet>> serverPackets = Arrays.asList(VoteCreatePacket::encode);
    private static final List<Function<PacketBuffer, Packet>> clientPackets = Arrays.asList(AnswerPacket::encode);

    private List<Task> tasks = new ArrayList<>();
    private static Socket s;
    private final NetworkHandler handler;
    private int packetlength = 0, packetlenghtMax;
    private VarInt templength;
    private int packetID = -1;
    private PacketBuffer rbuf;
    private PacketBuffer.PacketBufferWriter wbuf;
    public byte[] temp;
    private final boolean onServer;
    private ClientStorage clientStorage;
    private NetworkThread(String host, int port) {
        onServer = false;
        try {
            this.s = new Socket(host, port);
        }catch (IOException e) {
            e.printStackTrace();
        }
        clientStorage = new ClientStorage();
        handler = new ClientNetworkHandler(this);
    }

    private NetworkThread(Socket s) {
        onServer = true;
        this.s = s;
        handler = new ServerNetworkHandler(this);
    }

    protected static NetworkThread createServerThread(Socket s) {
        return new NetworkThread(s);
    }

    protected static NetworkThread createClientThread(String host, int port) {
        return new NetworkThread(host, port);
    }

    @Override
    public void run() {

    }

    public void next(int next) {
        System.out.println("" + next + "---" + (next & 0xFF) + " (" + (char)(next & 0xFF) + ")");
        if (packetlength == 0) {
            if (templength == null) {
                templength = new VarInt();
            }
            if (templength.next(next)) {
                packetlength = templength.getValue();
                packetlenghtMax = packetlength;
                temp = new byte[packetlength];
                packetID = -1;
                templength = null;
            }
            return;
        }
        temp[packetlenghtMax - packetlength] = (byte) next;
        packetlength--;
        if (packetlength == 0) {
            packetID = temp[0];
            rbuf = new PacketBuffer();
            wbuf = new PacketBuffer.PacketBufferWriter(rbuf);
            for(int i = 1; i < temp.length; i++) {
                wbuf.write((byte) temp[i]);
            }
            //System.out.println("Packet id: " + packetID + ", Status: " + status.name());
            //Packet p = status.getPackets()[packetID].apply(rbuf, version);
            //p.handle(this, handler);
        }
    }

    public void addTask(Task task) {

    }

    public void sendPacket(Packet packet) {
        PacketBuffer buf = new PacketBuffer();
        PacketBuffer.PacketBufferWriter wbuf = new PacketBuffer.PacketBufferWriter(buf);
        packet.decode(wbuf);
        System.out.println("Send " + packet.getClass().getName() + " with lenght of " + (buf.currentSize() + 1));
        byte[] content = buf.getAll();
        //System.out.println(content.length);
        byte[] lenght =VarInt.writeVarInt(buf.currentSize() + 1);
        byte[] temp = new byte[content.length + lenght.length + 1];
        int i = 0;
        for (byte b0:lenght) {
            temp[i] = b0;
            i++;
        }
        temp[i] = (byte) packet.id();
        i++;
        for (byte b0:content) {
            temp[i] = b0;
            i++;
        }
        try {
            OutputStream out = s.getOutputStream();
            out.write(temp);
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ClientStorage getClientStorage() {
        return clientStorage;
    }
}
