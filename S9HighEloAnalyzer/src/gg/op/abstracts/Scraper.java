package gg.op.abstracts;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

public abstract class Scraper<T> implements Callable<T> {
    protected Document document;

    public Scraper(String url) throws Exception {
        final int RETRY_TIMES = 3;

        for (int i = 0; i < RETRY_TIMES; i++) {
            try {
                document = Jsoup.connect(url).get();
                break;
            } catch (SocketTimeoutException ste) {
                System.err.println("Request was timed out. Exception details: " + ste.getMessage() + ". Retrying again in 5 seconds...");
                Thread.sleep(5000);
            }
        }

        if (document == null) {
            throw new RuntimeException("Could not retrieve the webpage with the specified URL.");
        }
    }

    @Override
    public T call() throws Exception {
        return scrape();
    }

    public abstract T scrape() throws Exception;
}
