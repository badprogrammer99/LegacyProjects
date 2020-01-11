package gg.op;

import gg.op.models.Rank;
import gg.op.models.Summoner;
import gg.op.runners.RankingLadderScraperRunner;
import gg.op.runners.PastSeasonsRankScraperRunner;
import gg.op.utils.UrlUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

class ExecuteScraperRunners {
    private static List<String> urls = UrlUtils.getListOfOpGgRegionUrls();
    private static ExecutorService rankingLadderScrapingRunnersExecutor = Executors.newScheduledThreadPool(urls.size());
    private static ExecutorService s8RankScrapingRunnersExecutor = Executors.newScheduledThreadPool(urls.size());
    private static Map<String, List<Summoner>> summonersList = new LinkedHashMap<>();

    static Map<String, List<Summoner>> runAndGetResults() throws InterruptedException, ExecutionException {
        List<Callable<Map<String, List<Summoner>>>> rankingLadderScraperRunnerCallables = new ArrayList<>();
        List<Callable<Map<String, List<Summoner>>>> pastSeasonsRankScraperRunnerCallables = new ArrayList<>();

        for (String url : urls) {
            rankingLadderScraperRunnerCallables.add(new RankingLadderScraperRunner.RankingLadderScrapingRunnerBuilder()
                    .setExecutor(Executors.newSingleThreadExecutor())
                    .setSleepTimeBetweenRequests(1500)
                    .setUrl(url)
                    .setScrapeUntilRank(new Rank(null, null, 1300))
                    .build());
        }

        List<Future<Map<String, List<Summoner>>>> summonersMaps = rankingLadderScrapingRunnersExecutor.invokeAll(rankingLadderScraperRunnerCallables);

        for (Future<Map<String, List<Summoner>>> summonersMap : summonersMaps) {
            pastSeasonsRankScraperRunnerCallables.add(new PastSeasonsRankScraperRunner.S8RankScrapingRunnerBuilder()
                    .setExecutor(Executors.newSingleThreadExecutor())
                    .setSleepTimeBetweenRequests(2000)
                    .setSummonersMap(summonersMap.get())
                    .build());
        }

        List<Future<Map<String, List<Summoner>>>> summonerMapsWithS8Ranks = s8RankScrapingRunnersExecutor.invokeAll(pastSeasonsRankScraperRunnerCallables);

        for (Future<Map<String, List<Summoner>>> summonerMapWithS8Ranks : summonerMapsWithS8Ranks) {
            summonersList.putAll(summonerMapWithS8Ranks.get());
        }

        return summonersList;
    }
}
