package net.freedomserg.projects.library.dbExecutors;

import net.freedomserg.projects.library.exception.InvalidInputException;

import java.util.Queue;

public abstract class DbExecutor {

    protected Queue<String> params;

    public void setParams(Queue<String> params) {
        this.params = params;
    }

    public abstract void execute();

    protected String retrieveBookName() {
        StringBuilder builder = new StringBuilder();
        String nextWord = params.peek();
        if (nextWord == null || !nextWord.startsWith("\"")) {
            throw new InvalidInputException("Invalid input. Try again.");
        }
        while(true) {
            String nameUnit = params.poll();
            builder.append(nameUnit).append(" ");
            if (nameUnit.endsWith("\"")) {
                break;
            }
        }
        return builder.toString().trim();
    }
}
