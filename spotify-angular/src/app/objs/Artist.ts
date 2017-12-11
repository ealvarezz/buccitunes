import {Album} from './album';
import {Concert} from './Concert';

export class Artist{
    id              : number;
    name            : string;
    biography       : string;
    avatar          : string;
    albums          : Album[];
    avatarPath      :string;
    concerts         : Concert[];

    // CHANGE THIS STUFF L8R
    followers       : string;
    features        : string;
    upcomingConcerts: string;
    featuredAlbum   : Album;
    recentlyPlayed  : Album[];

}