package gg.op.constants;

public class OPggRankingLadderPageClasses {

    public static final String TOP_5_SUMMONERS_CLASS = "ranking-highest__item";
    public static final String RANKING_TABLE_ROW_CLASS = "ranking-table__row";

    /**
     * Classes to scrape starting from the top summoner to the top 5th.
     */
    public static final String RANKING_HIGHER_POSITION_CLASS = "ranking-highest__rank";
    public static final String RANKING_HIGHER_SUMMONER_NAME_CLASS = "ranking-highest__name";
    public static final String RANKING_HIGHER_TIER_CLASS = "ranking-highest__tierrank";
    public static final String RANKING_HIGHER_LEVEL_CLASS = "ranking-highest__level";
    public static final String RANKING_HIGHER_WINRATIO_CLASS = "ranking-highest-winratio";

    /**
     * Scrape the winrate and wins/losses from both the top 1-5 summoners and the top 6 summoners and below.
     */
    public static final String CELL_WINRATIO_TEXT = "winratio__text";
    public static final String HOW_MANY_WINS = "winratio-graph__text winratio-graph__text--left";
    public static final String HOW_MANY_LOSSES = "winratio-graph__text winratio-graph__text--right";

    /**
     * Classes to scrape starting from the top 6 summoners and below.
     */
    public static final String CELL_SUMMONER_NAME_CLASS = "select_summoner ranking-table__cell ranking-table__cell--summoner";
    public static final String CELL_RANK_POSITION_CLASS = "ranking-table__cell ranking-table__cell--rank";
    public static final String CELL_TIER_CLASS = "ranking-table__cell ranking-table__cell--tier";
    public static final String CELL_LP_CLASS = "ranking-table__cell ranking-table__cell--lp";
    public static final String CELL_LEVEL_CLASS = "ranking-table__cell ranking-table__cell--level";
    public static final String CELL_WINRATIO_CLASS = "ranking-table__cell ranking-table__cell--winratio";
}
