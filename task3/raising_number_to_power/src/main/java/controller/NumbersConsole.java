package controller;

import java.util.HashMap;

public class NumbersConsole implements Communicatible{
    private double number;
    private int pow;

    public NumbersConsole(double number, int pow) {
        this.number = number;
        this.pow = pow;
    }

    public double getNumber() {

        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public int getPow() {
        return pow;
    }

    public void setPow(int pow) {
        this.pow = pow;
    }

    @Override
    public HashMap<String, Double> input() {
        return null;
    }

    @Override
    public void output(String data) {

    }
}
