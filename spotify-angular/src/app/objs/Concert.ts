import {Artist} from './Artist';
import {Location} from './Location';
import {User} from './User';
export class Concert{
    id              : number;
    name            : string;
    mainStar        : Artist; 
    releaseDate     : Date;
    price           : number;
    purchaseLink    : string;
    location        : Location = new Location();
    requester       : User;
    accept          : boolean;


}