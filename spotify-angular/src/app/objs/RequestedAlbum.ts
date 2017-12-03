import {Artist} from './Artist';
import {Song} from './Song';
import {User} from './User';
import {RequestedSong} from './RequestedSong';

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
    songs          : RequestedSong[] = [];
    dateCreated    : Date;
    comments       : string;
    requester      : User;
}