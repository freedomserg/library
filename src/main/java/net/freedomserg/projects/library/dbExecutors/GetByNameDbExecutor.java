package net.freedomserg.projects.library.dbExecutors;

import net.freedomserg.projects.library.model.entity.Book;
import net.freedomserg.projects.library.service.BookService;

import java.util.List;
import java.util.Map;

public class GetByNameDbExecutor extends DbExecutor {

    private BookService bookService;
    private String bookName;
    private Map<Integer, Book> books;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Map<Integer, Book> getBooks() {
        return books;
    }

    @Override
    public void execute() {
        List<Book> books = bookService.getByName(bookName);
        for (int i = 0; i < books.size(); i++) {
            this.books.put(i, books.get(i));
        }

    }
}
