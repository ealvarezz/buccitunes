
import {Album} from './Album';
import {Artist} from './Artist';
import {Playlist} from './Playlist';
import {BillingInfo} from './BillingInfo';
export class User{
    
    recentlyPlayed  : Album[];
    followingPlaylists : Playlist[];
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
    avatar          : String;
    billingInfo     : BillingInfo;
    artist          : Artist;
    inPrivateMode   : boolean;
    
}