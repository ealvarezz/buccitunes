import {Artist} from './Artist';
import {Location} from './Location';
export class Concert{
    id              : number;
    name            : string;
    mainStar        : Artist; 
    releaseDate     : Date;
    price           : number;
    purchaseLink    : string;
    location        : Location = new Location();


}