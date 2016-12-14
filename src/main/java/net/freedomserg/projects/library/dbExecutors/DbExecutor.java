package net.freedomserg.projects.library.dbExecutors;

import java.util.Queue;

public abstract class DbExecutor {

    protected Queue<String> params;

    public void setParams(Queue<String> params) {
        this.params = params;
    }

    public abstract void execute();
}
