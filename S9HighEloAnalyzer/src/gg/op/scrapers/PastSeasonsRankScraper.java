package gg.op.scrapers;

import gg.op.abstracts.Scraper;
import gg.op.constants.OPggSummonerPageClasses;
import gg.op.models.Rank;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class PastSeasonsRankScraper extends Scraper<List<Rank>> {

    public PastSeasonsRankScraper(String url) throws Exception {
        super(url);
    }

    @Override
    public List<Rank> scrape() throws Exception {
        Elements container = document.getElementsByClass(OPggSummonerPageClasses.PAST_RANKS_CONTAINER_CLASS);

        String s8Rank = getRankStringFromElement(container, 0, "S8 ");
        String s7Rank = getRankStringFromElement(container, 2, "S7 ");

        return new ArrayList<Rank>(){{
            add(safeNewRankFromString(s8Rank));
            add(safeNewRankFromString(s7Rank));
        }};
    }

    private String getRankStringFromElement(Elements container, int indexFromLastElement, String emptyTargetString) {
        try {
            return container.get(0)
                    .children()
                    .get(container.get(0).children().size() - (indexFromLastElement + 1))
                    .getElementsByClass("Item tip")
                    .attr("title")
                    .replace(emptyTargetString, "");
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private Rank safeNewRankFromString(String rankStringFromElement) {
        if (rankStringFromElement == null) return null;

        String[] splitRank = rankStringFromElement.split(" ");

        if (splitRank[1].contains("LP")) {
            return new Rank(splitRank[0], null,
                Integer.valueOf(splitRank[1]
                    .replace("LP", "")
                    .replace(",", "")
                )
            );
        }
        return new Rank(splitRank[0], Integer.valueOf(splitRank[1]),
            Integer.valueOf(splitRank[2]
                .replace("LP", "")
                .replace(",", "")
            )
        );
    }
}
