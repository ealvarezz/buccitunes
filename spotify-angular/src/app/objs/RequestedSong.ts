import {RequestedAlbum} from './RequestedAlbum';
import {Artist} from './Artist';
import {User} from './User';
export class RequestedSong{
    id : number;
    name : string;
    duration : number;
    album : RequestedAlbum;
    owner : Artist;
    // featuredArtists : Artist[];
    audioPath : string;
    audio : string;
    mimeType : string;
    genres : string[];
    picturePath : string;
    picture : string;
    explicit : boolean;
    comments : boolean;
    dateCreated : Date = new Date();
    approved : boolean;
    adminComments : string;
    requester : User;
}