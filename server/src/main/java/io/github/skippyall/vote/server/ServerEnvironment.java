package io.github.skippyall.vote.server;

import io.github.skippyall.vote.core.misc.EnvironmentVars;

import java.io.File;

public class ServerEnvironment {
    public static void init() {
        EnvironmentVars.dataFolder = new File("vote");
        EnvironmentVars.dataFolder.mkdir();
    }
}