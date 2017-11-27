import {Artist} from './Artist';
import {Song} from './Song';

export class RequestedAlbum{
    id             : number;
    title          : string;
    primaryArtist  : Artist;
    featuredArtist : Artist[];
    isASingle      : boolean;
    releaseDate    : Date = new Date();
    label          : string;
    artwork        : string;
    genres         : string[];
    songs          : Song[] = [];
    dateCreated    : Date;
    comments       : string;
}