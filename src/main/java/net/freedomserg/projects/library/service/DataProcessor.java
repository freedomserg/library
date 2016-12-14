package net.freedomserg.projects.library.service;

import net.freedomserg.projects.library.exception.InvalidInputException;
import net.freedomserg.projects.library.dbExecutors.DbExecutor;
import net.freedomserg.projects.library.service.DbExecutorService;

import java.util.*;

public class DataProcessor {

    private String input;
    private Queue<String> params;
    private static List<String> operations = new ArrayList<>(Arrays.asList("add", "remove", "edit", "all books"));

    public DataProcessor(String input) {
        this.input = input;
    }

    public void process() {
        String[] inputWords = input.split(" ");
        params = new ArrayDeque(Arrays.asList(inputWords));
        String operation = params.poll();
        if (operation != null) {
           if (operations.contains(operation.toLowerCase())) {
               DbExecutor dbWorker = DbExecutorService.getDbExecutor(operation, params);
               dbWorker.execute();
           } else {
                throw new InvalidInputException("Invalid input. Try again.");
           }
        }
    }
}
