package gg.op.scrapers;

import gg.op.models.Rank;
import gg.op.utils.RankingLadderPageUtils;

import gg.op.abstracts.Scraper;
import gg.op.constants.OPggRankingLadderPageClasses;
import gg.op.constants.OPggServicesUrls;
import gg.op.models.Summoner;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class RankingLadderScraper extends Scraper<List<Summoner>> {
    private String url;
    private int page;

    public RankingLadderScraper(String url, int page) throws Exception {
        super(url + OPggServicesUrls.RANKING_LADDER_URL + OPggServicesUrls.RANKING_LADDER_URL_PAGE_ARGUMENT + page);
        this.url = url + OPggServicesUrls.RANKING_LADDER_URL;
        this.page = page;
        System.out.println("Scraping server leaderboards at " + this.url + ", page " + page +  " ...");
    }

    @Override
    public List<Summoner> scrape() throws MalformedURLException {
        List<Summoner> summoners = new ArrayList<>();

        if (page == 1) {
            Elements top5Summoners = document.getElementsByClass(OPggRankingLadderPageClasses.TOP_5_SUMMONERS_CLASS);

            for (Element element : top5Summoners) {
                summoners.add(getSummonerFromTop5Ladder(element));
            }
        }

        Elements rankingTableLadder = document.getElementsByClass(OPggRankingLadderPageClasses.RANKING_TABLE_ROW_CLASS);

        for (Element element : rankingTableLadder) {
            summoners.add(getSummonerFromRankingLadder(element));
        }

        System.out.println("Top " + summoners.get(summoners.size() -1).getPositionInServer() + " summoners scraping operation from the URL "
                + url + " has been completed successfully.");

        return summoners;
    }

    private Summoner getSummonerFromTop5Ladder(Element element) throws MalformedURLException {
        Summoner summoner = new Summoner();

        summoner.setName(RankingLadderPageUtils.getHigherRankingSummonerName(element));
        summoner.setPositionInServer(RankingLadderPageUtils.getHigherRankingSummonerPosition(element));
        summoner.setRank(new Rank(RankingLadderPageUtils.getHigherRankingSummonerTier(element),
                    null, RankingLadderPageUtils.getHigherRankingSummonerLp(element)));
        summoner.setLevel(RankingLadderPageUtils.getHigherRankingSummonerLevel(element));
        summoner.setGamesPlayed(RankingLadderPageUtils.getHigherRankingSummonerPlayedGames(element));
        summoner.setWinRatio(RankingLadderPageUtils.getHigherRankingSummonerWinratio(element));
        summoner.setLinkToSelf(RankingLadderPageUtils.getHigherRankingSummonerUrl(element));

        return summoner;
    }

    private Summoner getSummonerFromRankingLadder(Element element) throws MalformedURLException {
        Summoner summoner = new Summoner();

        String[] summonerTier = RankingLadderPageUtils.getTierCell(element).split(" ");
        Integer lpPoints = RankingLadderPageUtils.getLpPointsCell(element);

        summoner.setName(RankingLadderPageUtils.getSummonerNameCell(element));
        summoner.setPositionInServer(RankingLadderPageUtils.getRankPositionCell(element));
        summoner.setRank(new Rank(summonerTier[0], summonerTier.length > 1 ? Integer.valueOf(summonerTier[1]) : null, lpPoints));
        summoner.setLevel(RankingLadderPageUtils.getLevelCell(element));
        summoner.setGamesPlayed(RankingLadderPageUtils.getGamesPlayedCell(element));
        summoner.setWinRatio(RankingLadderPageUtils.getWinratioCell(element));
        summoner.setLinkToSelf(RankingLadderPageUtils.getSummonerUrlCell(element));

        return summoner;
    }
}
