
export class BillingInfo{
    cardHolderName : string;
    creditCardNo : string;
    cvv : string
    billingAddresss : string;
    creditCardCompany : CreditCardCompany = new CreditCardCompany(null, null, null);
}

export class CreditCardCompany{
    id: number;
    name: string;
    artwork : string;

    constructor(id : number, name : string, artwork: string){
        this.id = id;
        this.name = name;
        this.artwork = artwork;
    }
}

export const CREDIT_CARD_ENUM=[
    new CreditCardCompany(1, "American Express", "/assets/amex.png"),
    new CreditCardCompany(2, "Discover", "/assets/discover.png"),
    new CreditCardCompany(3, "Master Card", "/assets/mastercard.png"),
    new CreditCardCompany(4, "Visa", "/assets/visa.png"),
];


