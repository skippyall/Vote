package io.github.skippyall.vote.fabric;

import io.github.skippyall.vote.core.misc.EnvironmentVars;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class FabricEnvironment {
    public static void init() {
        EnvironmentVars.dataFolder = new File(FabricLoader.getInstance().getConfigDir().toFile(), "vote");
        EnvironmentVars.dataFolder.mkdir();
    }
}
