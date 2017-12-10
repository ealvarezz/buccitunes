import {Song} from './Song';
import {Artist} from './Artist';
import {Album} from './Album';
import {Playlist} from './Playlist';

export class SearchResults{
songResults     : Song[]
artistResults : Artist[];
albumResults : Album[];
playlistResults : Playlist[];
}