package io.github.skippyall.vote.core.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    ServerSocket socket;

    ServerThread(int port) {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket s = socket.accept();
                NetworkThread.createServerThread(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
