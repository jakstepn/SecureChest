package io.github.jakstepn.Models;

import java.util.HashMap;
import java.util.Map;

public enum Security {
    EXTREME(5),
    HIGH(4),
    NORMAL(3),
    MINIMAL(2),
    BARELY_ANY(1),
    NOT_SECURE(0);

    private final int value;
    private static final Map<Integer, Security> lookup = new HashMap<>();

    static {
        for (Security s : Security.values()) {
            lookup.put(s.getValue(), s);
        }
    }

    Security(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public static Security get(int security) {
        return lookup.get(security);
    }
}
