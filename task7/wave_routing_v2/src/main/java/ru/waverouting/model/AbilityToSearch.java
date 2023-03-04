package ru.waverouting.model;

import java.util.Optional;

public interface AbilityToSearch {
    Optional<Route> lookForWayOut(Maze maze);
}
