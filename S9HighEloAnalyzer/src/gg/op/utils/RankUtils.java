package gg.op.utils;

import gg.op.constants.RanksMapping;

public class RankUtils {
    public static int getMappingForTier(String tier) {
        if (tier == null) return -1;
        if (tier.equalsIgnoreCase("Challenger")) return RanksMapping.CHALLENGER_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Grandmaster")) return RanksMapping.GRANDMASTER_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Master")) return RanksMapping.MASTER_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Diamond")) return RanksMapping.DIAMOND_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Platinum")) return RanksMapping.PLATINUM_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Gold")) return RanksMapping.GOLD_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Silver")) return RanksMapping.SILVER_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Bronze")) return RanksMapping.BRONZE_TIER_MAPPING_NUMBER;
        if (tier.equalsIgnoreCase("Iron")) return RanksMapping.IRON_TIER_MAPPING_NUMBER;

        throw new IllegalArgumentException("The provided tier argument is not valid.");
    }
}
