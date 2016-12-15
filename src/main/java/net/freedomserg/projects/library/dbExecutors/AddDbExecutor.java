package net.freedomserg.projects.library.dbExecutors;

import net.freedomserg.projects.library.exception.InvalidInputException;
import net.freedomserg.projects.library.service.BookService;

public class AddDbExecutor extends DbExecutor {

    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        String author = retrieveAuthor();
        String bookName = retrieveBookName();
        if(!params.isEmpty()) {
            throw new InvalidInputException("Invalid input. Try again.");
        }
        bookService.add(bookName, author);
    }

    private String retrieveAuthor() {
        StringBuilder builder = new StringBuilder();
        while(true) {
            String nextWord = params.peek();
            if (nextWord == null) {
                throw new InvalidInputException("Invalid input. Try again.");
            } else if (nextWord.startsWith("\"")) {
                break;
            }
            String authorUnit = params.poll();
            builder.append(authorUnit).append(" ");
        }
        return builder.toString().trim();
    }
}