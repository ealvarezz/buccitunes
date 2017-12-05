
import {Album} from './Album';
export class User{
    
    recentlyPlayed  : Album[];
    username        : string;
    first_name      : string;
    last_name       : string;
    email           : string;
    status          : string;
    following       : User[];
    isfollowing     : boolean;
    join_date       : Date;
    role            : String;
    avatarPath      : String;
    
}