import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Double.compare;

public class NumberTransformations {
    private static Scanner input = new Scanner(System.in);
    private static HashSet<ArrayList<DescriptionOperation>> commandsListSets = new HashSet<>();


    public static void main(String[] args) {
        startNumberTransformations();
    }

    private static void startNumberTransformations() {
        while (true){
            try{
                Double sourceNumber = getNumberFromUser("Enter a source number or empty line to cancel: ");
                Double targetNumber = getNumberFromUser("Enter a target number or empty line to cancel: ");

                Double multNumber = getNumberFromUser("Enter a number for multiplication operation or empty line to cancel: ");
                Operate<Double> operateMult = (x, y) -> x * y;

                Double addNumber = getNumberFromUser("Enter a number for addition operation or empty line to cancel: ");
                Operate<Double> operateAdd = (x, y) -> x + y;

                var operations = new HashSet<Operation<Double>>();
                operations.add(new Operation(operateMult, multNumber, DescriptionOperation.MULTIPLICATION));
                operations.add(new Operation(operateAdd, addNumber, DescriptionOperation.ADDITION));

                System.out.println("\nFinded Solutions:");
                commandsListSets.clear();
                var solutions = new HashSet<Solution<Double>>();
                solutions.add(new Solution(new ArrayList<Operation<Double>>(), sourceNumber));

                while (!solutions.isEmpty()){
                    solutions = findSolutions(solutions, operations, targetNumber);
                }

                if (commandsListSets.isEmpty()){
                    System.out.println("\nSolution not found");
                } else {
                    ArrayList<DescriptionOperation> commandsListMinSets = commandsListSets.stream().min(Comparator.comparing(ArrayList::size)).get();
                    System.out.print("\nMinimum number of commands: " + commandsListMinSets.size());
                    System.out.print(" ( First founded solution: ");
                    commandsListMinSets.forEach(minSets -> System.out.print(minSets.getCode() + " "));
                    System.out.print(")\n");
                }

                System.out.println("\nPress \"ENTER\" to continue...\n");
                input.nextLine();

            } catch (EmptyLineToCancelException e){
                System.out.println("Canceled");
                input.close();
                break;
            }
        }
    }

    private static HashSet<Solution<Double>> findSolutions(HashSet<Solution<Double>> solutions,
                                                    HashSet<Operation<Double>> operations,
                                                    Double targetNumber){
        var solution = solutions.iterator().next();

        for (Operation<Double> operation: operations) {
            Operate<Double> operate = operation.getOperate();
            var operationResult = operate.simpleOperate(solution.getResult(), operation.getOperateParam());

            if (operationResult > targetNumber){
                continue;
            }

            if (compare(operationResult, targetNumber) == 0) {
                var operationsList = new ArrayList<>(solution.getOperations());
                operationsList.add(operation);
                List<DescriptionOperation> commandsList = operationsList.stream()
                        .map(oper -> oper.getDescriptionOperation())
                        .collect(Collectors.toList());

                if (!commandsListSets.contains(commandsList)){
                    commandsListSets.add(new ArrayList<>(commandsList));
                    commandsList.forEach(command -> System.out.print(command.getCode() + " "));
                    System.out.println();
                }
            }

            if (operationResult < targetNumber){
                var operationsList = new ArrayList<>(solution.getOperations());
                operationsList.add(operation);
                solutions.add(new Solution(operationsList, operationResult));
            }
        }
        solutions.remove(solution);

        return solutions;
    }

    private static Double getNumberFromUser(String mess) throws EmptyLineToCancelException{
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

    private static class EmptyLineToCancelException extends Exception {
        private EmptyLineToCancelException (String message) {
            super(message);
        }
    }
}