package com.buccitunes.constants;

public enum UserRole {

	USER(0),
	PREMIUM(1),
	ARTIST(2),
	ADMIN(3);
	
    private int code;

    UserRole(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserRole fromCode(int code) {
        for (UserRole tier : UserRole.values()){
            if (tier.getCode() == code){
                return tier;
            }
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }
}
