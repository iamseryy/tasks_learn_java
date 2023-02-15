package controller;

import java.util.HashMap;

public interface Communicatible {
    HashMap<String, Double> input();
    void output(String data);
}
