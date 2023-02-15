package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class NumbersFile implements Communicatible{
    public static final String PATH_TO_PROPERTIES = "resources/config.properties";
    private double number;
    private int pow;

    public NumbersFile(double number, int pow) {
        this.number = number;
        this.pow = pow;
    }

    public static double getNumber() {
        double number = 0.0;
        ArrayList values = readFile(getProperty("file.input"));
        System.out.println(values.toString());

        return number;
    }

    private static File getProperty(String key){
        Properties property = new Properties();
        try(FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)){
            property.load(inputStream);
        }catch (IOException e){
            System.out.println("File not found: " + PATH_TO_PROPERTIES);
            e.printStackTrace();
        }

        return new File(property.getProperty(key));
    }

    private static ArrayList<String> readFile(File file){
        var data = new ArrayList<String>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while((line = reader.readLine()) != null){
                data.add(line);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        return data;
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
