package net.freedomserg.projects.library.dbExecutors;

import net.freedomserg.projects.library.exception.InvalidInputException;
import net.freedomserg.projects.library.service.BookService;

public class RemoveDbExecutor extends DbExecutor {

    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        String bookName = retrieveBookName();
        if(!params.isEmpty()) {
            throw new InvalidInputException("Invalid input. Try again.");
        }
        bookService.remove(bookName);
    }

    private String retrieveBookName() {
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
