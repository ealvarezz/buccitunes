import {Song} from './Song';
import {Artist} from './Artist';
import {Album} from './Album';
import {Playlist} from './Playlist';
import {User} from './User';

export class SearchResults{
songResults     : Song[]
artistResults : Artist[];
albumResults : Album[];
playlistResults : Playlist[];
userResults      : User[];
}