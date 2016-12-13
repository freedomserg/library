package net.freedomserg.projects.library.model.dao;

import net.freedomserg.projects.library.model.entity.Book;

import java.util.List;

public interface BookDao {

    Integer save(Book book);

    void delete(Book book);

    void update(Book book);

    Book loadById(int id);

    Book load(Book book);

    List<Book> loadByName(String name);

    List<Book> loadAll();
}
