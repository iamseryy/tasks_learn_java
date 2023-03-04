package ru.waverouting.service;

import ru.waverouting.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class WaveRouting {

    public static Optional<Route> findWay(Maze maze, Cat cat){
        var map = maze.toTreeMap();
        map.put(cat.location(), MazeFileElement.CAT);

        var searchMap = new HashMap<Point, MazeFileElement>(map)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().value));

        if (!checkExitNearby(searchMap)){
            spreadWave(searchMap);
            Optional<Point> nearestExitPoint = findNearestExitPoint(searchMap);

            if (nearestExitPoint.isEmpty()){
                return Optional.empty();
            }

            return Optional.ofNullable(buildRoute(nearestExitPoint.get(), searchMap));
        }

        return Optional.empty();
    }

    public static Route buildRoute(Point routePoint, Map<Point, Integer> searchMap) {
        var route = new Route(new HashSet<>());
        route.locations().add(routePoint);

        int routeValue = searchMap.get(routePoint);
        while (routeValue > 1){
            var aroundPoints =  getPointsAroundSourcePoint(routePoint);
            for (Point aroundPoint: aroundPoints){
                if(searchMap.get(aroundPoint) == routeValue - 1){
                    routePoint = aroundPoint;
                    route.locations().add(routePoint);
                    routeValue = searchMap.get(routePoint);
                    break;
                }
            }
        }

        return route;
    }

    public static Optional<Point> findNearestExitPoint(Map<Point, Integer> searchMap) {
        var exitPoints = new HashSet<Point>(searchMap.entrySet().stream()
                .filter(e-> e.getValue() == MazeFileElement.EXIT.value)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .keySet());

        var exitPointMap = new HashMap<Point, Integer>();

        for (Point point: exitPoints) {
            var aroundPoints =  getPointsAroundSourcePoint(point);
            for (Point arountPoint: aroundPoints) {
                Integer valueAroundPoint = searchMap.get(arountPoint);
                if (valueAroundPoint > 0){
                    exitPointMap.put(arountPoint, valueAroundPoint);
                }
            }
        }

        if(exitPointMap.size() == 0){
            return Optional.empty();
        }

        return Optional.ofNullable(Collections.min(exitPointMap.entrySet(), Map.Entry.comparingByValue()).getKey());
    }

    public static void spreadWave(Map<Point, Integer> searchMap) {
        var startPoint = searchMap.entrySet().stream()
                .filter(e-> e.getValue() == MazeFileElement.CAT.value)
                .findFirst()
                .get()
                .getKey();

        var pointSet = new HashSet<Point>();
        pointSet.add(startPoint);

        while(!pointSet.isEmpty()){
            var currentPoint = pointSet.iterator().next();
            int currentValue = searchMap.get(currentPoint);
            if (currentValue == MazeFileElement.CAT.value){
                currentValue = 0;
            }

            var aroundPoints =  getPointsAroundSourcePoint(currentPoint);
            for (Point arountPoint: aroundPoints) {
                if(searchMap.get(arountPoint) == 0 || searchMap.get(arountPoint) > currentValue + 1){
                    searchMap.put(arountPoint, currentValue + 1);
                    pointSet.add(arountPoint);
                }
            }

            pointSet.remove(currentPoint);
        }
    }

    private static HashSet<Point> getPointsAroundSourcePoint(Point sourcePoint){
        var targetSet = new HashSet<Point>();
        targetSet.add(new Point(sourcePoint.x(), sourcePoint.y() + 1));
        targetSet.add(new Point(sourcePoint.x() + 1, sourcePoint.y()));
        targetSet.add(new Point(sourcePoint.x(), sourcePoint.y() - 1));
        targetSet.add(new Point(sourcePoint.x() - 1, sourcePoint.y()));
        return targetSet;
    }

    public static boolean checkExitNearby(Map<Point, Integer> searchMap) {
        var startPoint = searchMap.entrySet().stream()
                .filter(e-> e.getValue() == MazeFileElement.CAT.value)
                .findFirst()
                .get()
                .getKey();

        var aroundPoints =  getPointsAroundSourcePoint(startPoint);
        for (Point aroundPoint: aroundPoints){
            if(searchMap.get(aroundPoint) == MazeFileElement.EXIT.value){
                return true;
            }
        }
        return false;
    }
}
