package net.freedomserg.projects.library.model.hibernate;

import net.freedomserg.projects.library.exception.MoreThanOneBookToEditException;
import net.freedomserg.projects.library.exception.MoreThanOneBookToRemoveException;
import net.freedomserg.projects.library.exception.NoSuchBookException;
import net.freedomserg.projects.library.exception.SuchBookAlreadyExistsException;
import net.freedomserg.projects.library.model.dao.BookDao;
import net.freedomserg.projects.library.model.entity.Book;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class HbookDao implements BookDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Book newBook) {
        try{
            Book target = load(newBook);
            throw new SuchBookAlreadyExistsException
                    ("Such newBook already exists in the library: " +
                            "id = " + target.getId() +
                            " bookName = " + target.getName() +
                            " author = " + target.getAuthor());
        } catch (NoSuchBookException ex) {
            return (Integer) sessionFactory.getCurrentSession().save(newBook);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(String bookName) {
        List<Book> books = loadByName(bookName);
        if (books.isEmpty()) {
            throw new NoSuchBookException("No such book with name " + bookName);
        }
        if (books.size() > 1) {
            throw new MoreThanOneBookToRemoveException("More than one book with such name to remove", bookName);
        }
        sessionFactory.getCurrentSession().delete(books.get(0));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(Book book) {
        sessionFactory.getCurrentSession().delete(book);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(String bookName, String newBookName) {
        List<Book> books = loadByName(bookName);
        if (books.isEmpty()) {
            throw new NoSuchBookException("No such book with name " + bookName);
        }
        if (books.size() > 1) {
            throw new MoreThanOneBookToEditException
                    ("More than one book with such name to edit", bookName, newBookName);
        }
        Book book = books.get(0);
        book.setName(newBookName);
        sessionFactory.getCurrentSession().update(book);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Book modifiedBook) {
        sessionFactory.getCurrentSession().update(modifiedBook);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Book loadById(int id) {
        return sessionFactory.getCurrentSession().load(Book.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Book load(Book targetBook) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT b FROM Book b WHERE b.name like :name AND b.author like :author");
        query.setParameter("name", targetBook.getName());
        query.setParameter("author", targetBook.getAuthor());
        Book target;
        try{
            target = (Book) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NoSuchBookException
                    ("No existing newBook with " +
                            "bookName = " + targetBook.getName() +
                            " author = " + targetBook.getAuthor());
        }
        return target;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Book> loadByName(String bookName) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT b FROM Book b WHERE b.name like :name");
        query.setParameter("name", bookName);
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Book> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT b FROM Book b order by b.name").list();
    }
}
