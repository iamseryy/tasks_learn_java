package ru.waverouting.model;

import ru.waverouting.service.WaveRouting;

import java.util.*;

public record Cat(Point location) implements AbilityToSearch{
    @Override
    public Optional<Route> lookForWayOut(Maze maze) {
        return WaveRouting.findWay(maze, this);
    }

    public boolean isInsideMaze(Maze maze){
        if(!maze.freeSpace().locations().contains(this.location())){
            return false;
        }
        return true;
    }
}
