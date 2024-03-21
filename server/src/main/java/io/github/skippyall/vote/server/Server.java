package io.github.skippyall.vote.server;

import io.github.skippyall.vote.core.VoteCore;
import io.github.skippyall.vote.core.config.Config;
import io.github.skippyall.vote.core.network.NetworkManager;

import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerEnvironment.init();
        VoteCore.init();
        NetworkManager.startServer(Config.getPort());
        Scanner in = new Scanner(System.in);
        while(true){
            if (in.next().equals("stop")) {
                VoteCore.shutdown();
                break;
            }
        }
    }
}
