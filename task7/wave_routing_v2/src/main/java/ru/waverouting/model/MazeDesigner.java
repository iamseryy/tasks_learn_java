package ru.waverouting.model;

public class MazeDesigner {
    private MazeBuilder builder;

    public MazeDesigner(MazeBuilder builder){
        super();
        this.builder = builder;
    }

    public Maze buildMaze(){
        return builder.buildFreeSpaces().buildWalls().buildExits().build();
    }
}
