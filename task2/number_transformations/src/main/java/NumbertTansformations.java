import java.util.Scanner;

public class NumbertTansformations {

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        startNumbertTansformations();
    }

    private static void startNumbertTansformations() {

    }

    public static int getNumberFromUser(String mess) throws EmptyLineToCancelException{
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

    public static class EmptyLineToCancelException extends Exception {
        public EmptyLineToCancelException (String message) {
            super(message);
        }
    }
}
