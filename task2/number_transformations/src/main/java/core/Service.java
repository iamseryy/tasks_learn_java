package core;

import controller.UserController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.compare;

public class Service {
    public static void findSolutions(double sourceNumber, double targetNumber, double multNumber, double addNumber){
        Operate<Double> operateMult = (x, y) -> x * y;
        Operate<Double> operateAdd = (x, y) -> x + y;
        var operations = new HashSet<Operation<Double>>();
        operations.add(new Operation(operateMult, multNumber, DescriptionOperation.MULTIPLICATION));
        operations.add(new Operation(operateAdd, addNumber, DescriptionOperation.ADDITION));

        var solutions = new HashSet<Solution<Double>>();
        solutions.add(new Solution(new ArrayList<Operation<Double>>(), sourceNumber));
        var commandsListSets = new HashSet<ArrayList<DescriptionOperation>>();

        UserController.message("\nFound Solutions:\n");

        while (!solutions.isEmpty()){
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
                        commandsList.forEach(command -> UserController.message(command.getCode() + " "));
                        UserController.message("\n");
                    }
                }

                if (operationResult < targetNumber){
                    var operationsList = new ArrayList<>(solution.getOperations());
                    operationsList.add(operation);
                    solutions.add(new Solution(operationsList, operationResult));
                }
            }
            solutions.remove(solution);
        }

        if (commandsListSets.isEmpty()){
            UserController.message("\nSolution not found\n");
        } else {
            ArrayList<DescriptionOperation> commandsListMinSets = commandsListSets.stream().min(Comparator.comparing(ArrayList::size)).get();
            UserController.message("\nMinimum number of commands: " + commandsListMinSets.size());
            UserController.message(" ( First founded solution: ");
            commandsListMinSets.forEach(minSets -> UserController.message(minSets.getCode() + " "));
            UserController.message(")\n");
        }
    }
}
