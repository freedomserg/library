package net.freedomserg.projects.library.model.dao;

import net.freedomserg.projects.library.model.entity.Book;

import java.util.List;

public interface BookDao {

    Integer save(Book newBook);

    void delete(String bookName);

    void delete(Book book);

    void update(String bookName, String newBookName);

    Book loadById(int id);

    Book load(Book targetBook);

    List<Book> loadByName(String bookName);

    List<Book> loadAll();
}
