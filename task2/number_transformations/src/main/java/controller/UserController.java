package controller;

import core.Service;
import exception.EmptyLineToCancelException;

import java.util.Scanner;

public class UserController {

    private static Scanner input = new Scanner(System.in);

    public static void start(){
        while (true){
            try{
                Double sourceNumber = getNumberFromUser("Enter a source number or empty line to cancel: ");
                Double targetNumber = getNumberFromUser("Enter a target number or empty line to cancel: ");
                Double multNumber = getNumberFromUser("Enter a number for multiplication operation or empty line to cancel: ");
                Double addNumber = getNumberFromUser("Enter a number for addition operation or empty line to cancel: ");

                Service.findSolutions(sourceNumber, targetNumber, multNumber, addNumber);

                System.out.println("\nPress \"ENTER\" to continue...");
                input.nextLine();
            } catch (EmptyLineToCancelException e){
                System.out.println("Canceled");
                input.close();
                break;
            }
        }
    }

    private static Double getNumberFromUser(String mess) throws EmptyLineToCancelException {
        Double number = null;
        do{
            System.out.print(mess);
            String line = input.nextLine();

            if (line.isEmpty()){
                throw new EmptyLineToCancelException("Canceled");
            }

            try{
                number = Double.parseDouble(line);
            } catch(Exception e){
                System.out.println("Not a number");
            }
        } while (number == null);

        return number;
    }

    public static void message(String message){
        System.out.print(message);
    }
}
