package io.github.skippyall.vote.core.network;

public class NetworkManager {
    private static NetworkThread NT;
    private static ServerThread ST;

    public static void startClient(String host, int port) {
        if (NT != null) return;
        NT = NetworkThread.createClientThread(host, port);
    }

    public static void startServer(int port) {
        if (ST != null) return;
        ST = new ServerThread(port);
    }
}
