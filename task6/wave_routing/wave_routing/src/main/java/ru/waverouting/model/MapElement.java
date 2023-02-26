package ru.waverouting.model;

import java.util.HashMap;
import java.util.Map;

public enum MapElement {
    FREE_SPACE(" ", 0),
    WALL("▓", -1),
    CAT("C", -2),
    WAY("*", -3),
    EXIT("E", -4);

    private static final Map<String, MapElement> BY_ICON = new HashMap<>();
    private static final Map<Integer, MapElement> BY_VALUE = new HashMap<>();

    static {
        for (MapElement e : values()) {
            BY_ICON.put(e.icon, e);
            BY_VALUE.put(e.value, e);
        }
    }

    public final String icon;
    public final int value;

    private MapElement(String icon, int value) {
        this.icon = icon;
        this.value = value;
    }

    public static MapElement valueOfIcon(String icon) {
        return BY_ICON.get(icon);
    }

    public static MapElement valueOfValue(int value) {
        return BY_VALUE.get(value);
    }
}
