package ru.waverouting.model;

import java.util.HashSet;

public record FreeSpace (HashSet<Point> locations) {}
