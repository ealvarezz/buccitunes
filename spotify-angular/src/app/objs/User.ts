
import {Album} from './Album';
import {BillingInfo} from './BillingInfo';
export class User{
    
    recentlyPlayed  : Album[];
    username        : string;
    name            : string;
    first_name      : string;
    last_name       : string;
    email           : string;
    status          : string;
    following       : User[];
    isfollowing     : boolean;
    join_date       : Date;
    role            : String;
    avatarPath      : String;
    billingInfo     : BillingInfo;
    
}