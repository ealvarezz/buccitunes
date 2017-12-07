import { Injectable, Inject} from '@angular/core';
import {Song } from '../objs/Song';
import {Album} from '../objs/Album';
import {Playlist} from '../objs/Playlist';
import {BucciResponse} from '../objs/BucciResponse';
import { environment } from '../../environments/environment';
import {BucciConstants} from '../../environments/app.config';
import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class QueueService {

    constructor() { }

    musicQueueChange : BehaviorSubject<Song[]>;

    get queue() : Song[]{return this.musicQueueChange.value;}

    addAlbumToQueue(album : Album){
        let temp = this.queue;
        for(let song of album.songs){
            temp.push(song);
        }
        this.musicQueueChange.next(temp);
    }

    playNewSong(song){
        this.musicQueueChange.next([]);
        let album = song.album;
        let index = album.indexOf(song);

        let newQueue = album.songs.slice(Math.max(album.songs.length - index, 1))
    }

    
}
