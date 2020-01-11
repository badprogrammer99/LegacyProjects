package gg.op.runners;

import gg.op.abstracts.AbstractScrapingRunner;
import gg.op.models.Rank;
import gg.op.models.Summoner;
import gg.op.scrapers.PastSeasonsRankScraper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@SuppressWarnings("WeakerAccess")
public class PastSeasonsRankScraperRunner extends AbstractScrapingRunner<Map<String, List<Summoner>>, List<Rank>> {
    private Map<String, List<Summoner>> summonersMap;

    public PastSeasonsRankScraperRunner(ExecutorService executor, Map<String, List<Summoner>> summonersMap) {
        super(executor);
        this.summonersMap = summonersMap;
    }

    public PastSeasonsRankScraperRunner(ExecutorService executor, Integer sleepTimeBetweenRequests, Map<String, List<Summoner>> summonersMap) {
        super(executor, sleepTimeBetweenRequests);
        this.summonersMap = summonersMap;
    }

    @Override
    public Map<String, List<Summoner>> call() throws Exception {
        for (Map.Entry<String, List<Summoner>> entry: summonersMap.entrySet()) {
            for (Summoner summoner : entry.getValue()) {
                Future<List<Rank>> ranks = scraperRunnerService.run(new PastSeasonsRankScraper(summoner.getLinkToSelf().toString()));

                if (ranks.get() != null) {
                    System.out.println("Summoner "
                        + summoner.getName()
                        + ", at rank position " + summoner.getPositionInServer()
                        + ", in the page "
                        + entry.getKey()
                        + ", S9 " + summoner.getRank().getTier()
                        + " "  + summoner.getRank().getLp()
                        + " LP was S8 " + ranks.get().get(0)
                        + " and S7 " + ranks.get().get(1) + "!");
                }

                summoner.setS8Rank(ranks.get().get(0));
                summoner.setS7Rank(ranks.get().get(1));
            }
        }

        return summonersMap;
    }

    public static class S8RankScrapingRunnerBuilder {
        private ExecutorService executor;
        private Integer sleepTimeBetweenRequests;
        private Map<String, List<Summoner>> summonersMap;

        public S8RankScrapingRunnerBuilder setExecutor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        public S8RankScrapingRunnerBuilder setSleepTimeBetweenRequests(Integer sleepTimeBetweenRequests) {
            this.sleepTimeBetweenRequests = sleepTimeBetweenRequests;
            return this;
        }

        public S8RankScrapingRunnerBuilder setSummonersMap(Map<String, List<Summoner>> summonersMap) {
            this.summonersMap = summonersMap;
            return this;
        }

        public PastSeasonsRankScraperRunner build() {
            if (sleepTimeBetweenRequests != null) return new PastSeasonsRankScraperRunner(executor, sleepTimeBetweenRequests, summonersMap);
            return new PastSeasonsRankScraperRunner(executor, summonersMap);
        }
    }
}
