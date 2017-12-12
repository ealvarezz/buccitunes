import {Artist} from './Artist';
import {Album} from './Album';

export class Song{
    public id               : number;
    public name             : string = "Homecoming";
    public duration         : number = 280;
    public rating           : number;
    public explicit       : boolean = false;
    public owner            : Artist;
    public featuredArtists  : Artist[];
    public mimeType         : string;
    public genres           : string[];
    public picturePath      : string;
    public audioPath        : string;
    public audio            : string;
    public lyrics           : string;
    public saved            : boolean;
    public album            : Album;
    // public album_image      : string = "/assets/graduation.png";
    // public song_file        : string = "/assets/rockstar.mp3";
    //public album            : String = "Graduation"

}


export const Genres = {
    1 : "Hip Hop",
    3 : "Pop",
    4 : "Latin",
    7 : "RNB",
    8 : "Metal",
    11 : "Rock",
    14 : "Punk",
    15 : "Indie"
}