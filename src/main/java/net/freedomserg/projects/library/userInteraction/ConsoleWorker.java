package net.freedomserg.projects.library.userInteraction;

import java.io.IOException;

public class ConsoleWorker {

    private static String initMessage;
    private static final String CANCEL = "cancel";

    static{
        StringBuilder builder = new StringBuilder();
        builder.append("Please, enter desirable operation in the following format: \n");
        builder.append("\t\t<add> <book_author> <\"book_name\">\n");
        builder.append("\t\t<remove> <\"book_name\">\n");
        builder.append("\t\t<edit> <\"book_name\">\n");
        builder.append("\t\t<all books>\n");
        builder.append("\t\tor type <cancel> to exit\n");
        initMessage = builder.toString();
    }

    public static void doInteraction() throws IOException {
        IOUtils.printToConsole(initMessage);

        String input = IOUtils.getUserInput();
        while(!CANCEL.equals(input.trim().toLowerCase())) {

            DataProcessor processor = new DataProcessor(input);

            IOUtils.printToConsole(initMessage);
            input = IOUtils.getUserInput();
        }
    }
}
