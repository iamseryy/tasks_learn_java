package ru.waverouting.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public record Maze(FreeSpace freeSpace, Wall wall, Exit exits) implements Printable{

    public TreeMap<Point, MazeFileElement> toTreeMap(){

        var sortedMap = new TreeMap<Point, MazeFileElement>();

        for (Point point : wall.locations()){
            sortedMap.put(point,MazeFileElement.WALL);
        }

        for (Point point : freeSpace.locations()){
            sortedMap.put(point,MazeFileElement.FREE_SPACE);
        }

        for (Point point : exits.locations()){
            sortedMap.put(point,MazeFileElement.EXIT);
        }

        return sortedMap;
    }

    @Override
    public void print(Maze maze, Cat cat) {
        TreeMap<Point, MazeFileElement> routeMap = maze.toTreeMap();
        routeMap.put(cat.location(), MazeFileElement.CAT);

        int y = 0;
        for (Map.Entry<Point, MazeFileElement> entry : routeMap.entrySet()) {
            if (entry.getKey().y() > y){
                y++;
                System.out.println();
            }
            System.out.print(entry.getValue().icon);
        }
    }

    @Override
    public String toString() {
        int y = 0;
        var maze = new StringBuilder();

        for (Map.Entry<Point, MazeFileElement> entry : this.toTreeMap().entrySet()) {
            if (entry.getKey().y() > y){
                y++;
                maze.append("\n");
            }
            maze.append(entry.getValue().icon);
        }

        return maze.toString();
    }
}
