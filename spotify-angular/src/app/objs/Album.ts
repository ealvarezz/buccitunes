import {Song} from './Song';

export class Album{
    id : number;
    releaseDate : Date = new Date();
    label : string;
    artwork: File;
    songs : Song[] = [];
    featuredArtists : String[] = [];
    

}