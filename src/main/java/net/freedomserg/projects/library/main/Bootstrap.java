package net.freedomserg.projects.library.main;

import net.freedomserg.projects.library.dbExecutors.EditDbExecutor;
import net.freedomserg.projects.library.dbExecutors.GetByNameDbExecutor;
import net.freedomserg.projects.library.dbExecutors.RemoveDbExecutor;
import net.freedomserg.projects.library.exception.InvalidInputException;
import net.freedomserg.projects.library.exception.MoreThanOneBookToEditException;
import net.freedomserg.projects.library.exception.MoreThanOneBookToRemoveException;
import net.freedomserg.projects.library.exception.SuchBookAlreadyExistsException;
import net.freedomserg.projects.library.model.entity.Book;
import net.freedomserg.projects.library.service.DataProcessor;
import net.freedomserg.projects.library.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Bootstrap {
    private Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    private static ApplicationContext applicationContext;

    private static final String INIT_MESSAGE;
    private static final String FEW_BOOKS_MESSAGE;
    private static final String CANCEL = "cancel";
    private static final String DONE = "done\n";

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("Please, enter desirable operation in the following format: \n");
        builder.append("\t\t<add> <book_author> <\"book_name\">\n");
        builder.append("\t\t<remove> <\"book_name\">\n");
        builder.append("\t\t<edit> <\"book_name\"> <\"new_book_name\">\n");
        builder.append("\t\t<all books>\n");
        builder.append("\t\tor type <cancel> to exit\n");
        INIT_MESSAGE = builder.toString();

        builder = new StringBuilder();
        builder.append("We have a few books with such name. Please choose one by typing a number of a book ");
        builder.append("or type <cancel> to exit: \n");
        FEW_BOOKS_MESSAGE = builder.toString();
    }


    public static void main(String[] args) throws IOException {
        applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        Bootstrap bootstrap = applicationContext.getBean("bootstrap", Bootstrap.class);
        bootstrap.start();
    }

    private void start() throws IOException {
        IOUtils.printToConsole(INIT_MESSAGE);

        String input = IOUtils.getUserInput();
        while (!CANCEL.equals(input.trim().toLowerCase())) {
            try {
                DataProcessor.process(input);
                IOUtils.printToConsole(DONE);
            } catch (InvalidInputException ex1) {
                logger.error(ex1.getMessage());
                IOUtils.printToConsole(INIT_MESSAGE);
            } catch (SuchBookAlreadyExistsException ex2) {
                logger.error(ex2.getMessage());
            } catch (MoreThanOneBookToRemoveException ex3) {
                logger.error(ex3.getMessage());
                Book bookToBeRemoved = chooseBook(ex3.getBookName());
                if (bookToBeRemoved != null) {
                    removeBook(bookToBeRemoved);
                    IOUtils.printToConsole(DONE);
                }
            } catch (MoreThanOneBookToEditException ex4) {
                logger.error(ex4.getMessage());
                Book bookToBeUpdated = chooseBook(ex4.getBookName());
                if (bookToBeUpdated != null) {
                    bookToBeUpdated.setName(ex4.getNewBookName());
                    editBook(bookToBeUpdated);
                    IOUtils.printToConsole(DONE);
                }
            }
            input = IOUtils.getUserInput();
        }
    }

    private Book chooseBook(String bookName) throws IOException {
        Book bookToBeRemoved = null;
        Map<Integer, Book> books = getBooks(bookName);
        IOUtils.printToConsole(FEW_BOOKS_MESSAGE);
        for (Map.Entry<Integer, Book> entry : books.entrySet()) {
            IOUtils.printToConsole(entry.getKey() + 1 + " " + entry.getValue().toString());
            IOUtils.printToConsole("\n");
        }
        String input = IOUtils.getUserInput();

        Set<Integer> bookKeySet = books.keySet();
        int selectedKey;
        boolean validKeySelected = false;
        while (!validKeySelected && !CANCEL.equals(input.trim().toLowerCase())) {
            try {
                selectedKey = Integer.parseInt(input) - 1;
                if (bookKeySet.contains(selectedKey)) {
                    validKeySelected = true;
                    bookToBeRemoved = books.get(selectedKey);
                } else {
                    logger.error("Invalid input. Try again.");
                    input = IOUtils.getUserInput();
                }
            } catch (NumberFormatException ex4) {
                logger.error("Invalid input. Try again.");
                input = IOUtils.getUserInput();
            }
        }
        return bookToBeRemoved;
    }

    private Map<Integer, Book> getBooks(String bookName) {
        GetByNameDbExecutor getByNameDbExecutor =
                applicationContext.getBean("getByNameDbExecutor", GetByNameDbExecutor.class);
        getByNameDbExecutor.setBookName(bookName);
        getByNameDbExecutor.execute();
        return getByNameDbExecutor.getBooks();
    }

    private void removeBook(Book bookToBeRemoved) {
        RemoveDbExecutor removeDbExecutor =
                applicationContext.getBean("removeDbExecutor", RemoveDbExecutor.class);
        removeDbExecutor.execute(bookToBeRemoved);
    }

    private void editBook(Book bookToBeUpdated) {
        EditDbExecutor editDbExecutor =
                applicationContext.getBean("editDbExecutor", EditDbExecutor.class);
        editDbExecutor.execute(bookToBeUpdated);
    }
}
