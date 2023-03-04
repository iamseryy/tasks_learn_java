package ru.waverouting.model;

public record Point(Integer x, Integer y) implements Comparable<Point> {
    @Override
    public int compareTo(Point point) {
        int result = this.y.compareTo(point.y);
        if (result == 0){
            result = this.x.compareTo(point.x);
        }
        return result;
    }
}
