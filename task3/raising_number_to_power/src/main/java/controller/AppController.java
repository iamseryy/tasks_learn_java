package controller;

import config.AppConfig;
import exception.CorruptedInputsException;
import util.FileUtils;
import util.Mathematics;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AppController {
    public static final Logger LOGGER = Logger.getLogger(AppController.class.getName());

    public static void start(){
        LOGGER.log(Level.INFO, "Application started");

        try{
            File fileInput = AppConfig.getProperty("file.input");
            ArrayList<String> data = FileUtils.readFile(fileInput);
            LOGGER.log(Level.INFO, "File " + fileInput + " read");

            HashMap<String, Optional<Number>> args = getArgs(data);
            Double number = (Double) args.get("number").orElseThrow(() -> new CorruptedInputsException("Canceled! Corrupted input data"));
            Integer pow = (Integer) args.get("pow").orElseThrow(() -> new CorruptedInputsException("Canceled! Corrupted input data"));
            LOGGER.log(Level.INFO, "Data got: " + "a = " + number + " ; b = " + pow);

            Double result = Mathematics.pow(number, pow);
            LOGGER.log(Level.INFO, "Result calculated: " + result);

            File fileOutput = AppConfig.getProperty("file.output");
            FileUtils.writeFile(String. valueOf(result), fileOutput);
            LOGGER.log(Level.INFO, "The result was written to the file: " + fileOutput);

        } catch (CorruptedInputsException | NumberFormatException | IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private static HashMap<String, Optional<Number>> getArgs(ArrayList<String> data)
                                                                throws NumberFormatException, CorruptedInputsException{
        var args = new HashMap<String, Optional<Number>>();

        if (data.size() != 2){
            data.clear();
            throw new CorruptedInputsException("Corrupted input data");
        }

        args.put("number", Optional.of(Double.parseDouble(data.stream()
                                        .filter(item -> item.split(" ")[0].equals("a"))
                                        .findAny()
                                        .orElseThrow(() -> new CorruptedInputsException("Corrupted input data"))
                                        .split(" ")[1]
                                        .trim())));

        args.put("pow", Optional.of(Integer.parseInt(data.stream()
                                        .filter(item -> item.split(" ")[0].equals("b"))
                                        .findAny()
                                        .orElseThrow(() -> new CorruptedInputsException("Corrupted input data"))
                                        .split(" ")[1]
                                        .trim())));
        return args;
    }
}

