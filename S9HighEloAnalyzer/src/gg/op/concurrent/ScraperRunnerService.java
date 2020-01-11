package gg.op.concurrent;

import gg.op.abstracts.Scraper;

import java.util.concurrent.Future;

public interface ScraperRunnerService<V> {
    Future<V> run(Scraper<V> task);
}
