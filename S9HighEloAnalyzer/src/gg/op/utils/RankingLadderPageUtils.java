package gg.op.utils;

import gg.op.constants.OPggRankingLadderPageClasses;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;

public class RankingLadderPageUtils {
    public static String getHigherRankingSummonerName(Element element) {
        return element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_SUMMONER_NAME_CLASS).text();
    }

    public static Integer getHigherRankingSummonerPosition(Element element) {
        return Integer.valueOf(element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_POSITION_CLASS).text());
    }

    public static String getHigherRankingSummonerTier(Element element) {
        return RankingLadderResultUtils.getTierFromHighestRank(element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_TIER_CLASS).text());
    }

    public static Integer getHigherRankingSummonerLp(Element element) {
       return RankingLadderResultUtils.getLpFromHighestRank(element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_TIER_CLASS).text());
    }

    public static Integer getHigherRankingSummonerLevel(Element element) {
        return Integer.valueOf(element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_POSITION_CLASS).text()) == 1
                ? Integer.valueOf(element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_LEVEL_CLASS).text())
                : RankingLadderResultUtils.getLvlFromTop2ToTop5(element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_LEVEL_CLASS).text());
    }

    public static Integer getHigherRankingSummonerPlayedGames(Element element) {
        return RankingLadderResultUtils.getGamesPlayedFromCellElements(new Elements[] {
            element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_WINRATIO_CLASS).get(0)
                    .getElementsByClass(OPggRankingLadderPageClasses.HOW_MANY_WINS),
            element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_WINRATIO_CLASS).get(0)
                    .getElementsByClass(OPggRankingLadderPageClasses.HOW_MANY_LOSSES)
        });
    }

    public static Integer getHigherRankingSummonerWinratio(Element element) {
        return RankingLadderResultUtils.getWinratioStringFromCell(element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_WINRATIO_CLASS)
                    .get(0)
                    .getElementsByClass(OPggRankingLadderPageClasses.CELL_WINRATIO_TEXT).text());
    }

    public static URL getHigherRankingSummonerUrl(Element element) throws MalformedURLException {
        return new URL("https:" + element.getElementsByClass(OPggRankingLadderPageClasses.RANKING_HIGHER_SUMMONER_NAME_CLASS)
                    .select("a")
                    .attr("href"));
    }

    public static String getSummonerNameCell(Element element) {
       return element.getElementsByClass(OPggRankingLadderPageClasses.CELL_SUMMONER_NAME_CLASS).text();
    }

    public static Integer getRankPositionCell(Element element) {
        return Integer.valueOf(element.getElementsByClass(OPggRankingLadderPageClasses.CELL_RANK_POSITION_CLASS).text());
    }

    public static String getTierCell(Element element) {
        return element.getElementsByClass(OPggRankingLadderPageClasses.CELL_TIER_CLASS).text();
    }

    public static Integer getLpPointsCell(Element element) {
        return RankingLadderResultUtils.getLpStringFromCell(element.getElementsByClass(OPggRankingLadderPageClasses.CELL_LP_CLASS).text());
    }

    public static Integer getLevelCell(Element element) {
        return Integer.valueOf(element.getElementsByClass(OPggRankingLadderPageClasses.CELL_LEVEL_CLASS).text());
    }

    public static Integer getGamesPlayedCell(Element element) {
        return RankingLadderResultUtils.getGamesPlayedFromCellElements(new Elements[] {
            element.getElementsByClass(OPggRankingLadderPageClasses.CELL_WINRATIO_CLASS).get(0).getElementsByClass(OPggRankingLadderPageClasses.HOW_MANY_WINS),
            element.getElementsByClass(OPggRankingLadderPageClasses.CELL_WINRATIO_CLASS).get(0).getElementsByClass(OPggRankingLadderPageClasses.HOW_MANY_LOSSES)
        });
    }

    public static Integer getWinratioCell(Element element) {
       return RankingLadderResultUtils.getWinratioStringFromCell(element.getElementsByClass(OPggRankingLadderPageClasses.CELL_WINRATIO_CLASS)
                    .get(0)
                    .getElementsByClass(OPggRankingLadderPageClasses.CELL_WINRATIO_TEXT).text());
    }

    public static URL getSummonerUrlCell(Element element) throws MalformedURLException {
        return new URL("https:" + element.getElementsByClass(OPggRankingLadderPageClasses.CELL_SUMMONER_NAME_CLASS)
                    .select("a")
                    .attr("href"));
    }

    /**
     * Internal class used for some page specific result manipulation.
     */
    private static class RankingLadderResultUtils {
        private static String getTierFromHighestRank(String tierLpString) {
            return tierLpString.split(" ")[0];
        }

        private static Integer getLpFromHighestRank(String tierLpString) {
            return Integer.valueOf(tierLpString.split(" ")[1].replace(",", ""));
        }

        private static Integer getLvlFromTop2ToTop5(String level) {
            return Integer.valueOf(level.replace("Lv.", ""));
        }

        private static Integer getLpStringFromCell(String lp) {
            String[] splitLp = lp.split(" ");

            return Integer.valueOf(splitLp[0].replace(",", ""));
        }

        private static Integer getWinratioStringFromCell(String winratio) {
            return Integer.valueOf(winratio.replace("%", ""));
        }

        private static Integer getGamesPlayedFromCellElements(Elements[] element) {
            Elements winsDiv = element[0];
            Elements lossesDiv = element[1];

            if ("".equals(lossesDiv.text())) {
                return Integer.valueOf(winsDiv.text());
            }

            return Integer.valueOf(winsDiv.text()) + Integer.valueOf(lossesDiv.text());
        }
    }
}
