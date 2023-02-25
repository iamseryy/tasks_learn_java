package ru.waverouting.controller;

import ru.waverouting.config.AppConfig;
import ru.waverouting.model.MapElement;
import ru.waverouting.model.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AppController {
    public static void start(){
        var map = getMap();

        var searchMap = new HashMap<Point, MapElement>(map)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().value));

        Point startPoint = searchMap.entrySet().stream().filter(e-> e.getValue() == -10).findFirst().get().getKey();
        var pointSet = new HashSet<Point>();
        pointSet.add(startPoint);

        while(!pointSet.isEmpty()){
            var currentPoint = pointSet.iterator().next();
            int currentValue = searchMap.get(currentPoint);
            if (currentValue == -10){
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

        printMap(map);
        System.out.println();
        printSearchMap(searchMap);
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