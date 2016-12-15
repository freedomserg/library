package net.freedomserg.projects.library.dbExecutors;

import net.freedomserg.projects.library.exception.InvalidInputException;
import net.freedomserg.projects.library.model.entity.Book;
import net.freedomserg.projects.library.service.BookService;

public class EditDbExecutor extends DbExecutor {

    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        String bookName = retrieve();
        String newBookName = retrieve();
        if(!params.isEmpty()) {
            throw new InvalidInputException("Invalid input. Try again.");
        }
        bookService.edit(bookName, newBookName);
    }

    public void execute(Book bookToBeUpdated) {
        bookService.edit(bookToBeUpdated);
    }

    private String retrieve() {
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
