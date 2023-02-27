package ru.waverouting.service;

import ru.waverouting.model.MapElement;
import ru.waverouting.model.Point;

import java.util.*;
import java.util.stream.Collectors;

public class AppService {
    public static Optional<Point> findNearestExitPoint(Map<Point, Integer> searchMap) {
        var exitPoints = new HashSet<Point>(searchMap.entrySet().stream()
                .filter(e-> e.getValue() == MapElement.EXIT.value)
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

    public static void buildRoute(Point routePoint, Map<Point, Integer> searchMap, HashMap<Point, MapElement> map) {
        map.put(routePoint, MapElement.WAY);
        int routeValue = searchMap.get(routePoint);
        while (routeValue > 1){
            var aroundPoints =  getPointsAroundSourcePoint(routePoint);
            for (Point aroundPoint: aroundPoints){
                if(searchMap.get(aroundPoint) == routeValue - 1){
                    routePoint = aroundPoint;
                    map.put(routePoint, MapElement.WAY);
                    routeValue = searchMap.get(routePoint);
                    break;
                }
            }
        }
    }

    public static void spreadWave(Map<Point, Integer> searchMap) {
        Point startPoint = searchMap.entrySet().stream()
                .filter(e-> e.getValue() == MapElement.CAT.value)
                .findFirst()
                .get()
                .getKey();

        var pointSet = new HashSet<Point>();
        pointSet.add(startPoint);

        while(!pointSet.isEmpty()){
            var currentPoint = pointSet.iterator().next();
            int currentValue = searchMap.get(currentPoint);
            if (currentValue == MapElement.CAT.value){
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
        Point startPoint = searchMap.entrySet().stream()
                .filter(e-> e.getValue() == MapElement.CAT.value)
                .findFirst()
                .get()
                .getKey();

        var aroundPoints =  getPointsAroundSourcePoint(startPoint);
        for (Point aroundPoint: aroundPoints){
            if(searchMap.get(aroundPoint) == MapElement.EXIT.value){
                return true;
            }
        }
        return false;
    }
}
