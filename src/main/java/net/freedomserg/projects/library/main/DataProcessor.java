package net.freedomserg.projects.library.main;

import net.freedomserg.projects.library.exception.InvalidInputException;
import net.freedomserg.projects.library.dbExecutors.DbExecutor;
import net.freedomserg.projects.library.service.DbExecutorService;

import java.util.*;

public class DataProcessor {

    private static List<String> validOperations = new ArrayList<>(Arrays.asList("add", "remove", "edit", "all"));

    public static void process(String input) {
        String[] inputWords = input.split(" ");
        Queue<String> params = new ArrayDeque(Arrays.asList(inputWords));
        String operation = params.poll();
        if (operation != null) {
           if (validOperations.contains(operation.toLowerCase())) {
               DbExecutor dbExecutor = DbExecutorService.getDbExecutor(operation, params);
               dbExecutor.execute();
           } else {
                throw new InvalidInputException("Invalid input. Try again.");
           }
        }
    }
}
