package net.freedomserg.projects.library.main;

import java.io.IOException;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
        ConsoleWorker consoleWorker = new ConsoleWorker();
        consoleWorker.start();
    }
}
