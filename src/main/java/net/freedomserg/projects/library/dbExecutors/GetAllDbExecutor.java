package net.freedomserg.projects.library.dbExecutors;

import net.freedomserg.projects.library.model.entity.Book;
import net.freedomserg.projects.library.service.BookService;

import java.util.List;

public class GetAllDbExecutor extends DbExecutor {

    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        List<Book> books = bookService.getAll();
        books.forEach(System.out::println);
    }
}
