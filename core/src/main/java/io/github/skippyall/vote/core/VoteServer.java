package io.github.skippyall.vote.core;

import io.github.skippyall.vote.core.config.Config;
import io.github.skippyall.vote.core.storage.Storages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoteServer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Vote");
    public static void init(){
        Config.load();
        Storages.loadServer();
    }

    public static void shutdown(){
        Storages.save();
        Config.save();
    }
}
