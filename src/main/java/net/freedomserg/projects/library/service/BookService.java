package net.freedomserg.projects.library.service;

import net.freedomserg.projects.library.model.dao.BookDao;
import net.freedomserg.projects.library.model.entity.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BookService {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Transactional
    public void add(String bookName, String author) {
        Book book = createNewBook(bookName, author);
        bookDao.save(book);
    }

    @Transactional
    public void remove(String bookName) {
        bookDao.delete(bookName);
    }

    @Transactional
    public void remove(Book book) {
        bookDao.delete(book);
    }

    @Transactional
    public void edit(String bookName, String newBookName) {
        bookDao.update(bookName, newBookName);
    }

    @Transactional
    public void edit(Book modifiedBook) {
        bookDao.update(modifiedBook);
    }

    @Transactional
    public List<Book> getByName(String bookName) {
        return bookDao.loadByName(bookName);
    }

    @Transactional
    public List<Book> getAll() {
        return bookDao.loadAll();
    }

    private Book createNewBook(String bookName, String author) {
        Book book = new Book();
        book.setName(bookName);
        book.setAuthor(author);
        return book;
    }
}
