package gg.op;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());

        Map<String, List<gg.op.models.Summoner>> scrapingResults = ExecuteScraperRunners.runAndGetResults();

        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\jcarlosi\\IdeaProjects\\S9Analyzer\\ScrapingResults.data");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(scrapingResults);
        objectOut.close();

        /* ApiConfig config = new ApiConfig().setKey("RGAPI-366740f5-2877-471b-93e2-9294351138f4");
        RiotApi api = new RiotApi(config);

        Summoner summoner = api.getSummonerByName(Platform.EUW, "Rito IQ is 3Head");

        MatchList matchList = api.getMatchListByAccountId(Platform.EUW, summoner.getAccountId());
        Match match = api.getMatch(Platform.EUW, matchList.getMatches().get(0).getGameId()); */

        System.exit(0);
    }
}
