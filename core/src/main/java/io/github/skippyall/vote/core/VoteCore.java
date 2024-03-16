package io.github.skippyall.vote.core;

import io.github.skippyall.vote.core.config.Config;
import io.github.skippyall.vote.core.storage.Storages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoteCore {
    public static final Logger LOGGER = LoggerFactory.getLogger("VoteCore");
    public static void init(){
        Config.load();
        Storages.load();
    }

    public static void shutdown(){
        Storages.save();
        Config.save();
    }
}
