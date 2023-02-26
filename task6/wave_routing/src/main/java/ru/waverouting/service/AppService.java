package ru.waverouting.service;

import ru.waverouting.model.MapElement;
import ru.waverouting.model.Point;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class AppService {
    public static Point findNearestPoint(Map<Point, Integer> searchMap) {
        var exitPoints = new HashSet<Point>(searchMap.entrySet().stream()
                .filter(e-> e.getValue() == MapElement.EXIT.value)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .keySet());

        var exitMap = new HashMap<Point, Integer>();
        for (Point point: exitPoints) {
            Point previoslyPoint = new Point(point.x(), point.y() + 1);
            Integer valuePrevioslyPoint = searchMap.get(previoslyPoint);
            if (valuePrevioslyPoint > 0){
                exitMap.put(previoslyPoint, valuePrevioslyPoint);
            }

            previoslyPoint = new Point(point.x() + 1, point.y());
            valuePrevioslyPoint = searchMap.get(previoslyPoint);
            if (valuePrevioslyPoint > 0){
                exitMap.put(previoslyPoint, valuePrevioslyPoint);
            }

            previoslyPoint = new Point(point.x(), point.y() - 1);
            valuePrevioslyPoint = searchMap.get(previoslyPoint);
            if (valuePrevioslyPoint > 0){
                exitMap.put(previoslyPoint, valuePrevioslyPoint);
            }

            previoslyPoint = new Point(point.x() - 1, point.y());
            valuePrevioslyPoint = searchMap.get(previoslyPoint);
            if (valuePrevioslyPoint > 0){
                exitMap.put(previoslyPoint, valuePrevioslyPoint);
            }
        }

        return Collections.min(exitMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static void buildRoute(Point routePoint, Map<Point, Integer> searchMap, HashMap<Point, MapElement> map) {
        map.put(routePoint, MapElement.WAY);
        int routeValue = searchMap.get(routePoint);
        Point nextRoutePoint;
        while (routeValue > 0){
            nextRoutePoint = new Point(routePoint.x(), routePoint.y() + 1);
            if(searchMap.get(nextRoutePoint) == routeValue - 1){
                routePoint = nextRoutePoint;
                map.put(routePoint, MapElement.WAY);
                routeValue = searchMap.get(routePoint);
                continue;
            }

            nextRoutePoint = new Point(routePoint.x() + 1, routePoint.y());
            if(searchMap.get(nextRoutePoint) == routeValue - 1){
                routePoint = nextRoutePoint;
                map.put(routePoint, MapElement.WAY);
                routeValue = searchMap.get(routePoint);
                continue;
            }

            nextRoutePoint = new Point(routePoint.x(), routePoint.y() - 1);
            if(searchMap.get(nextRoutePoint) == routeValue - 1){
                routePoint = nextRoutePoint;
                map.put(routePoint, MapElement.WAY);
                routeValue = searchMap.get(routePoint);
                continue;
            }

            nextRoutePoint = new Point(routePoint.x() - 1, routePoint.y());
            if(searchMap.get(nextRoutePoint) == routeValue - 1){
                routePoint = nextRoutePoint;
                map.put(routePoint, MapElement.WAY);
                routeValue = searchMap.get(routePoint);
                continue;
            }

            routeValue = 0;
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

            Point nextPoint = new Point(currentPoint.x(), currentPoint.y() + 1);
            if(searchMap.get(nextPoint) == 0 || searchMap.get(nextPoint) > currentValue + 1){
                searchMap.put(nextPoint, currentValue + 1);
                pointSet.add(nextPoint);
            }

            nextPoint = new Point(currentPoint.x() + 1, currentPoint.y());
            if(searchMap.get(nextPoint) == 0 || searchMap.get(nextPoint) > currentValue + 1){
                searchMap.put(nextPoint, currentValue + 1);
                pointSet.add(nextPoint);
            }

            nextPoint = new Point(currentPoint.x(), currentPoint.y() - 1);
            if(searchMap.get(nextPoint) == 0 || searchMap.get(nextPoint) > currentValue + 1){
                searchMap.put(nextPoint, currentValue + 1);
                pointSet.add(nextPoint);
            }

            nextPoint = new Point(currentPoint.x() - 1, currentPoint.y());
            if(searchMap.get(nextPoint) == 0 || searchMap.get(nextPoint) > currentValue + 1){
                searchMap.put(nextPoint, currentValue + 1);
                pointSet.add(nextPoint);
            }

            pointSet.remove(currentPoint);
        }
    }
}
