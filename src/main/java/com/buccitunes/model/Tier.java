package com.buccitunes.model;

public enum Tier {

	NITRO_DUBS_TIER(0),
    TREX_TIER(1),
    MOONMAN_TIER(2),
	NO_TIER(3);
	
    private int code;

    Tier(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Tier fromCode(int code) {
        for (Tier tier : Tier.values()){
            if (tier.getCode() == code){
                return tier;
            }
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }
}
