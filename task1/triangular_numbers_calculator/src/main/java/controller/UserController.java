package controller;

import exception.EmptyLineToCancelException;
import utils.Mathematics;

import java.util.Scanner;

public class UserController {

    private static Scanner input = new Scanner(System.in);

    public static void start(){
        while (true){
            try{
                int number = getNumberFromUser("Enter a non-negative integer or empty line to cancel: ");
                System.out.println("Result = " + Mathematics.triangularNumber(number));
            } catch (EmptyLineToCancelException e){
                System.out.println("Canceled");
                input.close();
                break;
            }
        }

    }

    private static int getNumberFromUser(String mess) throws EmptyLineToCancelException{
        int number = -1;
        do{
            System.out.print(mess);
            String line = input.nextLine();

            if (line.isEmpty()){
                throw new EmptyLineToCancelException("Canceled");
            }

            try{
                number = Integer.parseInt(line);
                if (number < 0){
                    System.out.println("It's must be a non-negative integer");
                }
            } catch(Exception e){
                System.out.println("Not an integer");
            }
        } while (number < 0);

        return number;
    }
}
