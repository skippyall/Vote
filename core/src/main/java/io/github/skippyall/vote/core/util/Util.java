package io.github.skippyall.vote.core.util;

import io.github.skippyall.vote.core.VoteServer;

import java.util.function.Supplier;

public class Util {
    public static  <T> T tryGetOrDefault(Supplier<T> toTry, T def, String warnMessage){
        try {
            return toTry.get();
        } catch (Exception e) {
            if(warnMessage != null) {
                VoteServer.LOGGER.warn(warnMessage, e);
            }
            return def;
        }
    }
}
