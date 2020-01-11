package gg.op.models;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

public class Summoner implements Serializable {
    private String name;
    private Integer positionInServer;
    private Rank rank;
    private Integer level;
    private Integer gamesPlayed;
    private Integer winRatio;
    private Rank s8Rank;
    private Rank s7Rank;
    private URL linkToSelf;

    public Summoner() { }

    public Summoner(String name, Integer positionInServer, Rank rank, Integer level, Integer gamesPlayed, Integer winRatio, Rank s8Rank,
                    Rank s7Rank, URL linkToSelf) {
        this.name = name;
        this.positionInServer = positionInServer;
        this.rank = rank;
        this.level = level;
        this.gamesPlayed = gamesPlayed;
        this.winRatio = winRatio;
        this.s8Rank = s8Rank;
        this.s7Rank = s7Rank;
        this.linkToSelf = linkToSelf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPositionInServer() {
        return positionInServer;
    }

    public void setPositionInServer(Integer positionInServer) {
        this.positionInServer = positionInServer;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getWinRatio() {
        return winRatio;
    }

    public void setWinRatio(Integer winRatio) {
        this.winRatio = winRatio;
    }

    public Rank getS8Rank() {
        return s8Rank;
    }

    public void setS8Rank(Rank s8Rank) {
        this.s8Rank = s8Rank;
    }

    public Rank getS7Rank() {
        return s7Rank;
    }

    public void setS7Rank(Rank s7Rank) {
        this.s7Rank = s7Rank;
    }

    public URL getLinkToSelf() {
        return linkToSelf;
    }

    public void setLinkToSelf(URL linkToSelf) {
        this.linkToSelf = linkToSelf;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, positionInServer, rank, level, gamesPlayed, winRatio, s8Rank, s7Rank, linkToSelf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summoner summoner = (Summoner) o;
        return  Objects.equals(name, summoner.name) &&
                Objects.equals(positionInServer, summoner.positionInServer) &&
                Objects.equals(rank, summoner.rank) &&
                Objects.equals(level, summoner.level) &&
                Objects.equals(gamesPlayed, summoner.gamesPlayed) &&
                Objects.equals(winRatio, summoner.winRatio) &&
                Objects.equals(s8Rank, summoner.s8Rank) &&
                Objects.equals(s7Rank, summoner.s7Rank) &&
                Objects.equals(linkToSelf, summoner.linkToSelf);
    }

    @Override
    public String toString() {
        return "Summoner {" +
                "name='" + name + '\'' +
                ", positionInServer='" + positionInServer + '\'' +
                ", rank=" + rank +
                ", level=" + level +
                ", gamesPlayed=" + gamesPlayed +
                ", winRatio=" + winRatio +
                ", s8Rank=" + s8Rank +
                ", s7Rank=" + s7Rank +
                ", linkToSelf=" + linkToSelf +
                '}';
    }
}
