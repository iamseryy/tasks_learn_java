package ru.waverouting.model;

import ru.waverouting.config.AppConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class MazeBuilderFromFile implements MazeBuilder{
    private FreeSpace freeSpace;
    private Wall wall;
    private Exit exits;

    @Override
    public MazeBuilder buildFreeSpaces() {
        this.freeSpace = new FreeSpace(getMazeElementsFromFile(MazeFileElement.FREE_SPACE));
        return this;
    }

    @Override
    public MazeBuilder buildWalls() {
        this.wall = new Wall(getMazeElementsFromFile(MazeFileElement.WALL));
        return this;
    }

    @Override
    public MazeBuilder buildExits() {
        this.exits = new Exit(getMazeElementsFromFile(MazeFileElement.EXIT));
        return this;
    }

    @Override
    public Maze build() {
        return new Maze(freeSpace, wall, exits);
    }

    private static HashSet<Point> getMazeElementsFromFile(MazeFileElement mazeFileElement){
        var mazeElements = new HashSet<Point>();
        try(BufferedReader reader = new BufferedReader(new FileReader(AppConfig.getProperty("maze.file")));){
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("");
                for(int x = 0; x < parts.length; x++){
                    if(mazeFileElement.icon.equals(parts[x])) {
                        mazeElements.add(new Point(x, y));
                    }
                }
                y++;
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return mazeElements;
    }
}
