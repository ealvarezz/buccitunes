import {Artist} from './Artist';
import {Album} from './Album';

export class Song{
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
