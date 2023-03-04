package ru.waverouting.model;

import java.util.HashMap;
import java.util.Map;

public enum MazeFileElement {
    FREE_SPACE(" ", 0),
    WALL("▓", -1),
    CAT("C", -2),
    ROUTE("*", -3),
    EXIT("E", -4);

    private static final Map<String, MazeFileElement> BY_ICON = new HashMap<>();
    private static final Map<Integer, MazeFileElement> BY_VALUE = new HashMap<>();

    static {
        for (MazeFileElement e : values()) {
            BY_ICON.put(e.icon, e);
            BY_VALUE.put(e.value, e);
        }
    }

    public final String icon;
    public final int value;

    private MazeFileElement(String icon, int value) {
        this.icon = icon;
        this.value = value;
    }

    public static MazeFileElement valueOfIcon(String icon) {
        return BY_ICON.get(icon);
    }

    public static MazeFileElement valueOfValue(int value) {
        return BY_VALUE.get(value);
    }
}
