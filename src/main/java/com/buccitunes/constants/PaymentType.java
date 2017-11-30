package com.buccitunes.constants;

public enum PaymentType {

	ROYALTY_PAYMENT(0),
    PROMOTIONNAL_PAYMENT(1);
	
    private int code;

    PaymentType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentType fromCode(int code) {
        for (PaymentType type : PaymentType.values()){
            if (type.getCode() == code){
                return type;
            }
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }
}
