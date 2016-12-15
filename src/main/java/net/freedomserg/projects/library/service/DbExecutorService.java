package net.freedomserg.projects.library.service;

import net.freedomserg.projects.library.dbExecutors.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Queue;

public class DbExecutorService {

    public static DbExecutor getDbExecutor(String operation, Queue params) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        switch(operation){
            case "add":
                AddDbExecutor addDbExecutor =
                        applicationContext.getBean("addDbExecutor", AddDbExecutor.class);
                addDbExecutor.setParams(params);
                return addDbExecutor;
            case "remove":
                RemoveDbExecutor removeDbExecutor =
                        applicationContext.getBean("removeDbExecutor", RemoveDbExecutor.class);
                removeDbExecutor.setParams(params);
                return removeDbExecutor;
            case "edit":
                EditDbExecutor editDbExecutor =
                        applicationContext.getBean("editDbExecutor", EditDbExecutor.class);
                editDbExecutor.setParams(params);
                return editDbExecutor;
            case "all":
                GetAllDbExecutor getAllDbExecutor =
                        applicationContext.getBean("getAllDbExecutor", GetAllDbExecutor.class);
                return getAllDbExecutor;
        }
        return null;
    }
}
