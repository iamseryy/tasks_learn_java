import java.util.*;
import java.util.stream.Collectors;

public class NumbertTansformations {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        startNumbertTansformations();
    }

    private static void startNumbertTansformations() {
        while (true){
//            try{
//                int sourceNumber = getNumberFromUser("Enter a source number or empty line to cancel: ");
                int sourceNumber = 2;
//                int targetNumber = getNumberFromUser("Enter a target number or empty line to cancel: ");
                int targetNumber = 9;
//                int multNumber = getNumberFromUser("Enter a number for multiplication operation or empty line to cancel: ");
                int multNumber = 2;
                Operate<Integer> operateMult = (x, y) -> x * y;

//                int addNumber = getNumberFromUser("Enter a number for addition operation or empty line to cancel: ");
                int addNumber = 1;
                Operate<Integer> operateAdd = (x, y) -> x + y;

                var operations = new HashSet<Operation<Integer>>();
                operations.add(new Operation(operateMult, multNumber, DescriptionOperation.MULTIPLICATION));
                operations.add(new Operation(operateAdd, addNumber, DescriptionOperation.ADDITION));

                HashSet<ArrayList<DescriptionOperation>> commandsList = getCommands(sourceNumber, targetNumber, operations);
                System.out.println(commandsList.toString());

                System.out.println("\nPress \"ENTER\" to continue...\n");
                input.nextLine();

//            } catch (EmptyLineToCancelException e){
//                System.out.println("Canceled");
//                input.close();
//                break;
//            }
        }
    }

    private static HashSet<ArrayList<DescriptionOperation>> getCommands(Integer sourceNumber,
                                                                        Integer targetNumber,
                                                                        HashSet<Operation<Integer>> operations) {
        var commandsListSets = new HashSet<ArrayList<DescriptionOperation>>();
        var operationResult = sourceNumber;
        var solutions = new HashSet<Solution<Integer>>();
        solutions.add(new Solution(new ArrayList<Operation<Integer>>(), sourceNumber));
        while (!solutions.isEmpty()){
            var solution = solutions.iterator().next();

            for (Operation<Integer> operation: operations) {
                Operate<Integer> operate = operation.getOperate();
                operationResult = operate.simpleOperate(operationResult, operationResult);

                if (operationResult > targetNumber){
                   continue;
                }

                if (operationResult == targetNumber){
                    List<Operation<Integer>> operationsList = solution.getOperations();
                    operationsList.add(operation);
                    List<DescriptionOperation> commandsList = operationsList.stream()
                                                                            .map(oper -> oper.getDescriptionOperation())
                                                                            .collect(Collectors.toList());
                    commandsListSets.add(new ArrayList<>(commandsList));
                }

                if (operationResult < targetNumber){
                    List<Operation<Integer>> operationsList = solution.getOperations();
                    operationsList.add(operation);
                    solutions.add(new Solution(operationsList, operationResult));
                }
            }
            solutions.remove(solution);
        }



        return commandsListSets;
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

    private static class EmptyLineToCancelException extends Exception {
        private EmptyLineToCancelException (String message) {
            super(message);
        }
    }
}
