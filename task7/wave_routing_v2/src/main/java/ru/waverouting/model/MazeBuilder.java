package ru.waverouting.model;

public interface MazeBuilder {
    MazeBuilder buildWalls();

    MazeBuilder buildFreeSpaces();

    MazeBuilder buildExits();

    Maze build();

}
