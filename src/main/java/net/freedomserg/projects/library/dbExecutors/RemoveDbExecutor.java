package net.freedomserg.projects.library.dbExecutors;

import net.freedomserg.projects.library.exception.InvalidInputException;
import net.freedomserg.projects.library.model.entity.Book;
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

    public void execute(Book book) {
        bookService.remove(book);
    }
}
