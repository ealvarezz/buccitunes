import {Album} from './album';

export class Artist{
    id              : number;
    name            : string;
    biography       : string;
    avatar          : string;
    albums          : Album[];
    avatarPath      :string;

    // CHANGE THIS STUFF L8R
    followers       : string;
    features        : string;
    upcomingConcerts: string;
    featuredAlbum   : Album;
    recentlyPlayed  : Album[];

}