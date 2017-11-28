import {Song} from './Song';
import {Artist} from './Artist';

export class Album{
    id              : number;
    releaseDate     : Date = new Date();
    artwork         : string;
    artworkPath     : string;
    dateCreated     : Date = new Date();
    featuredArtists : String[] = [];
    primaryArtist : Artist;
    //featuredArtist : Artist[] = [];
    genres          : string[] = [];
    label           : string;
    isPublic        : string;
    songs           : Song[] = [new Song(), new Song(), new Song(), new Song(), new Song(), new Song(), new Song(), new Song()];
    stats           : any;
    title           : string;

}