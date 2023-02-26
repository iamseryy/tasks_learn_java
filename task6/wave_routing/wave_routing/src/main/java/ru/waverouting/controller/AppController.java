package ru.waverouting.controller;

import ru.waverouting.config.AppConfig;
import ru.waverouting.model.MapElement;
import ru.waverouting.model.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AppController {
    public static void start(){
        var map = getMap();

        var searchMap = new HashMap<Point, MapElement>(map)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().value));

        spreadWave(searchMap);

        buildRoute(searchMap, map);

        printMap(map);
    }

    private static void buildRoute(Map<Point, Integer> searchMap, HashMap<Point, MapElement> map) {
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

        Point routePoint = Collections.min(exitMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        map.put(routePoint, MapElement.WAY);
        int routeValue = searchMap.get(routePoint);
        while (routeValue > 0){
            Point nextRoutePoint = new Point(routePoint.x(), routePoint.y() + 1);
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

    private static void spreadWave(Map<Point, Integer> searchMap) {
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


    private static HashMap<Point, MapElement> getMap(){
        var map = new HashMap<Point, MapElement>();
        try(BufferedReader reader = new BufferedReader(new FileReader(AppConfig.getProperty("map.file")));){
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("");
                for(int x = 0; x < parts.length; x++){
                    map.put(new Point(x, y), MapElement.valueOfIcon(parts[x]));
                }
                y++;
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    private static void printMap(HashMap<Point, MapElement> map){
        TreeMap<Point, MapElement> sortedMap = new TreeMap<>(map);
        int y = 0;
        for (Map.Entry<Point, MapElement> entry : sortedMap.entrySet()) {
            if (entry.getKey().y() > y){
                y++;
                System.out.println();
            }
            System.out.print(entry.getValue().icon);
        }
    }

    private static void printSearchMap(Map<Point, Integer> map){
        TreeMap<Point, Integer> sortedMap = new TreeMap<>(map);
        int y = 0;
        for (Map.Entry<Point, Integer> entry : sortedMap.entrySet()) {
            if (entry.getKey().y() > y){
                y++;
                System.out.println();
            }
            System.out.print(" " + entry.getValue() + " ");
        }
    }
}