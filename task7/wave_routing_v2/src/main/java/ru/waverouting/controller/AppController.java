package ru.waverouting.controller;

import ru.waverouting.model.*;


public class AppController {
    public static void start(){
        var builder = new MazeBuilderFromFile();
        var designer = new MazeDesigner(builder);
        var maze = designer.buildMaze();

        var cat = new Cat(new Point(3, 5));
        if (cat.isInsideMaze(maze)){
            var route = cat.lookForWayOut(maze);
            route.ifPresentOrElse(
                    routeMap -> {
                        System.out.println("\nThe cat found the nearest exit from the maze!\n");
                        routeMap.print(maze, cat);
                        }, () -> {
                                    System.out.println("\nNo way out of the maze!\n");
                                    maze.print(maze, cat);
                                });
        } else{
            System.out.println("\nThe cat is not inside the maze\n");
            maze.print(maze, cat);
        }
    }
}
