package net.freedomserg.projects.library.main;

import net.freedomserg.projects.library.dbExecutors.GetByNameDbExecutor;
import net.freedomserg.projects.library.exception.InvalidInputException;
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
    Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    private static final String INIT_MESSAGE;
    private static final String FEW_BOOKS_MESSAGE;
    private static final String CANCEL = "cancel";
    private static final String DONE = "done\n";

    static{
        StringBuilder builder = new StringBuilder();
        builder.append("Please, enter desirable operation in the following format: \n");
        builder.append("\t\t<add> <book_author> <\"book_name\">\n");
        builder.append("\t\t<remove> <\"book_name\">\n");
        builder.append("\t\t<edit> <\"book_name\">\n");
        builder.append("\t\t<all books>\n");
        builder.append("\t\tor type <cancel> to exit\n");
        INIT_MESSAGE = builder.toString();

        builder = new StringBuilder();
        builder.append("We have a few books with such name. Please choose one by typing a number of a book: \n");
        FEW_BOOKS_MESSAGE = builder.toString();
    }


    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        Bootstrap bootstrap = applicationContext.getBean("bootstrap", Bootstrap.class);
        bootstrap.start();
    }

    private void start() throws IOException {
        IOUtils.printToConsole(INIT_MESSAGE);

        String input = IOUtils.getUserInput();
        while(!CANCEL.equals(input.trim().toLowerCase())) {
            try {
                DataProcessor.process(input);
                IOUtils.printToConsole(DONE);
            }catch (InvalidInputException ex1) {
                logger.error(ex1.getMessage());
                IOUtils.printToConsole(INIT_MESSAGE);
            }catch (SuchBookAlreadyExistsException ex2) {
                logger.error(ex2.getMessage());
            }catch (MoreThanOneBookToRemoveException ex3) {
                logger.error(ex3.getMessage());
                String bookName = ex3.getBookName();
                GetByNameDbExecutor dbExecutor = new GetByNameDbExecutor();
                dbExecutor.setBookName(bookName);
                dbExecutor.execute();
                Map<Integer, Book> books = dbExecutor.getBooks();
                IOUtils.printToConsole(FEW_BOOKS_MESSAGE);

                for (Map.Entry<Integer, Book> entry : books.entrySet()) {
                    IOUtils.printToConsole(entry.getKey() + " " + entry.getValue().toString());
                }

                Set<Integer> keySet = books.keySet();
                input = IOUtils.getUserInput();
                //proceed deleting operation


            }
            input = IOUtils.getUserInput();
        }
    }
}
