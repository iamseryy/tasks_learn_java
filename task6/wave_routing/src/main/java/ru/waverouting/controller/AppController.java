package ru.waverouting.controller;

import ru.waverouting.config.AppConfig;
import ru.waverouting.model.MapElement;
import ru.waverouting.model.Point;
import ru.waverouting.service.AppService;

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

        if (!AppService.checkExitNearby(searchMap)){
            AppService.spreadWave(searchMap);
            Optional<Point> nearestExitPoint = AppService.findNearestExitPoint(searchMap);
            nearestExitPoint.ifPresentOrElse(point -> AppService.buildRoute(point, searchMap, map),
                                                () -> System.out.println("\nNo way out of the maze!"));
        }

        System.out.println();
        printMap(map);
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
}