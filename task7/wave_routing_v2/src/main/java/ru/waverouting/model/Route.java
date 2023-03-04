package ru.waverouting.model;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public record Route(HashSet<Point> locations) implements Printable {
    @Override
    public void print(Maze maze, Cat cat) {
        TreeMap<Point, MazeFileElement> routeMap = maze.toTreeMap();
        routeMap.put(cat.location(), MazeFileElement.CAT);
        for (Point point: this.locations()) {
            routeMap.put(point, MazeFileElement.ROUTE);
        }

        int y = 0;
        for (Map.Entry<Point, MazeFileElement> entry : routeMap.entrySet()) {
            if (entry.getKey().y() > y){
                y++;
                System.out.println();
            }
            System.out.print(entry.getValue().icon);
        }
    }
}
