package gg.op.runners;

import gg.op.abstracts.AbstractScrapingRunner;
import gg.op.models.Rank;
import gg.op.models.Summoner;
import gg.op.scrapers.RankingLadderScraper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@SuppressWarnings("WeakerAccess")
public class RankingLadderScraperRunner extends AbstractScrapingRunner<Map<String, List<Summoner>>, List<Summoner>> {
    private String url;
    private Rank scrapeUntilRank;

    public RankingLadderScraperRunner(ExecutorService executor, String url, Rank scrapeUntilRank) {
        super(executor);
        this.url = url;
        this.scrapeUntilRank = scrapeUntilRank;
    }

    public RankingLadderScraperRunner(ExecutorService executor, Integer sleepTimeBetweenRequests, String url, Rank scrapeUntilRank) {
        super(executor, sleepTimeBetweenRequests);
        this.url = url;
        this.scrapeUntilRank = scrapeUntilRank;
    }

    @Override
    public Map<String, List<Summoner>> call() throws Exception {
        Map<String, List<Summoner>> finalSummonersMap = new LinkedHashMap<>();
        finalSummonersMap.put(url, new ArrayList<>());

        int currentPage = 1;
        boolean isScrapingToBeContinued = true;

        do {
            Future<List<Summoner>> futureListSummoner = scraperRunnerService.run(new RankingLadderScraper(url, currentPage));
            List<Summoner> retrievedSummoners = futureListSummoner.get();

            int nonMatchingRecords = 0;

            for (Summoner summoner : retrievedSummoners) {
                if (scrapeUntilRank.compareTo(summoner.getRank()) > 0) {
                    nonMatchingRecords++;
                } else {
                    finalSummonersMap.get(url).add(summoner);
                }
            }

            if (nonMatchingRecords == retrievedSummoners.size()) {
                isScrapingToBeContinued = false;
            } else {
                currentPage++;
            }

        } while (isScrapingToBeContinued);

        return finalSummonersMap;
    }

    public static class RankingLadderScrapingRunnerBuilder {
        private ExecutorService executor;
        private Integer sleepTimeBetweenRequests;
        private String url;
        private Rank scrapeUntilRank;

        public RankingLadderScrapingRunnerBuilder setExecutor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        public RankingLadderScrapingRunnerBuilder setSleepTimeBetweenRequests(Integer sleepTimeBetweenRequests) {
            this.sleepTimeBetweenRequests = sleepTimeBetweenRequests;
            return this;
        }

        public RankingLadderScrapingRunnerBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public RankingLadderScrapingRunnerBuilder setScrapeUntilRank(Rank scrapeUntilRank) {
            this.scrapeUntilRank = scrapeUntilRank;
            return this;
        }

        public RankingLadderScraperRunner build() {
            if (sleepTimeBetweenRequests != null) return new RankingLadderScraperRunner(executor, sleepTimeBetweenRequests, url, scrapeUntilRank);
            return new RankingLadderScraperRunner(executor, url, scrapeUntilRank);
        }
    }
}
