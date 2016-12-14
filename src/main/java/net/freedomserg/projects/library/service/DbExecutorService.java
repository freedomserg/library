package net.freedomserg.projects.library.service;

import net.freedomserg.projects.library.userInteraction.AddingDbExecutor;
import net.freedomserg.projects.library.userInteraction.DbExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Queue;

public class DbExecutorService {

    public static DbExecutor getDbWorker(String operation, Queue params) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        switch(operation){
            case "add":
                AddingDbExecutor dbWorker = applicationContext.getBean("addDbWorker", AddingDbExecutor.class);
                dbWorker.setParams(params);
                return dbWorker;
            case "remove":


        }

        return null;
    }
}
