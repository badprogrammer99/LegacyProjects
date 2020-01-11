package gg.op.concurrent;

import gg.op.abstracts.Scraper;

import java.util.concurrent.*;

public class SimpleScraperRunnerService<T> extends ExecutorCompletionService<T> implements ScraperRunnerService<T> {
    private Integer sleepTimeBetweenRequests;

    public SimpleScraperRunnerService(Executor executor) {
        super(executor);
    }

    public SimpleScraperRunnerService(Executor executor, Integer sleepTimeBetweenRequests) {
        super(executor);
        this.sleepTimeBetweenRequests = sleepTimeBetweenRequests;
    }

    @Override
    public Future<T> run(Scraper<T> task) {
        if (sleepTimeBetweenRequests != null) {
            try {
                Thread.sleep(sleepTimeBetweenRequests);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return super.submit(task);
    }
}
