package gg.op.models;

import gg.op.utils.RankUtils;

import java.io.Serializable;
import java.util.Objects;

public class Rank implements Comparable<Rank>, Serializable {
    private String tier;
    private Integer division;
    private Integer lp;

    public Rank() {

    }

    public Rank(String tier, Integer division, Integer lp) {
        this.tier = tier;
        this.division = division;
        this.lp = lp;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Integer getDivision() {
        return division;
    }

    public void setDivision(Integer division) {
        this.division = division;
    }

    public Integer getLp() {
        return lp;
    }

    public void setLp(Integer lp) {
        this.lp = lp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tier, division, lp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return Objects.equals(tier, rank.tier) &&
                Objects.equals(division, rank.division) &&
                Objects.equals(lp, rank.lp);
    }

    @Override
    public String toString() {
        return "Rank {" +
                "tier='" + tier + '\'' +
                ", division=" + division +
                ", lp=" + lp +
                '}';
    }


    @Override
    public int compareTo(Rank otherRank) {
        if ((this.getDivision() != null && this.getTier() == null && this.getLp() == null)
                || (otherRank.getDivision() != null && otherRank.getTier() == null && otherRank.getLp() == null)) {
            throw new IllegalArgumentException("Can't compare ranks solely through their divisions.");
        }

        int thisRankTierMapping = RankUtils.getMappingForTier(this.getTier());
        int otherRankTierMapping = RankUtils.getMappingForTier(otherRank.getTier());

        if (thisRankTierMapping != -1 && otherRankTierMapping != -1) {
            if (thisRankTierMapping > otherRankTierMapping) {
                return 1;
            } else if (otherRankTierMapping > thisRankTierMapping){
                return -1;
            }
        }

        if (this.getDivision() != null && otherRank.getDivision() != null) {
            int thisRankDivision = this.getDivision();
            int otherRankDivision = otherRank.getDivision();

            if (otherRankDivision > thisRankDivision) {
                return 1;
            } else if (thisRankDivision > otherRankDivision) {
                return -1;
            }
        }

        if (this.getLp() != null && otherRank.getLp() != null) {
            int thisRankLp = this.getLp();
            int otherRankLp = otherRank.getLp();

            if (thisRankLp > otherRankLp) {
                return 1;
            } else if (otherRankLp > thisRankLp) {
                return -1;
            }
        }

        // if we reach there, it means the ranks are equal
        return 0;
    }
}
