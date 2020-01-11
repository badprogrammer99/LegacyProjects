package gg.op.abstracts;

import gg.op.concurrent.ScraperRunnerService;
import gg.op.concurrent.SimpleScraperRunnerService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public abstract class AbstractScrapingRunner<T, V> implements Callable<T> {
    protected ScraperRunnerService<V> scraperRunnerService;

    public AbstractScrapingRunner(ExecutorService executor) {
        this.scraperRunnerService = new SimpleScraperRunnerService<>(executor);
    }

    public AbstractScrapingRunner(ExecutorService executor, Integer sleepTimeBetweenRequests) {
        this.scraperRunnerService = new SimpleScraperRunnerService<>(executor, sleepTimeBetweenRequests);
    }
}
