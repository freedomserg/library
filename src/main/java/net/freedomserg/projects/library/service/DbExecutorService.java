package net.freedomserg.projects.library.service;

import net.freedomserg.projects.library.dbExecutors.AddDbExecutor;
import net.freedomserg.projects.library.dbExecutors.DbExecutor;
import net.freedomserg.projects.library.dbExecutors.RemoveDbExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Queue;

public class DbExecutorService {

    public static DbExecutor getDbExecutor(String operation, Queue params) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        switch(operation){
            case "add":
                AddDbExecutor addingDbExecutor =
                        applicationContext.getBean("addDbExecutor", AddDbExecutor.class);
                addingDbExecutor.setParams(params);
                return addingDbExecutor;
            case "remove":
                RemoveDbExecutor removingDbExecutor =
                        applicationContext.getBean("removeDbExecutor", RemoveDbExecutor.class);
                removingDbExecutor.setParams(params);
                return removingDbExecutor;

        }

        return null;
    }
}
