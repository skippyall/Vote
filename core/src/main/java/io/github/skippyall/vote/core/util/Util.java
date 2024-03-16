package io.github.skippyall.vote.core.util;

import io.github.skippyall.vote.core.VoteCore;

import java.util.function.Supplier;

public class Util {
    public static  <T> T tryGetOrDefault(Supplier<T> toTry, T def, String warnMessage){
        try {
            return toTry.get();
        } catch (Exception e) {
            if(warnMessage != null) {
                VoteCore.LOGGER.warn(warnMessage, e);
            }
            return def;
        }
    }
}
