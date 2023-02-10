import java.util.Scanner;

public class TriangularNumbersCalculator {
    public static void main(String[] args){

        try{
            int number = getNumberFromUser("Enter a non-negative integer or empty line to cancel: ");
            System.out.println("Result = " + String.valueOf(calculate(number)));
        } catch (EmptyLineToCancelException e){
            System.out.println("Canceled");
        }
    }

    public static int getNumberFromUser(String mess) throws EmptyLineToCancelException{
        Scanner input = new Scanner(System.in);
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

        input.close();

        return number;
    }

    public static int calculate(int number){
        return number * (number + 1) / 2;
    }

    public static class EmptyLineToCancelException extends Exception {
        public EmptyLineToCancelException (String message) {
            super(message);
        }
    }
}
