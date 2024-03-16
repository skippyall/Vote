package io.github.skippyall.vote.core.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EqualsMap<T,U> extends AbstractMap<T,U> {
    private final Set<Entry<T,U>> entrySet = new HashSet<>();

    @Override
    public Set<Entry<T,U>> entrySet() {
        return entrySet;
    }
}
