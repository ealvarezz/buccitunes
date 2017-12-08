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


    musicQueueChange : BehaviorSubject<Song[]>;
    historyChange    : BehaviorSubject<Song[]>;
    get history()    : Song[] {return this.historyChange.value}

    constructor() { 
        this.musicQueueChange = new BehaviorSubject([]);
        this.historyChange = new BehaviorSubject([]);
    }

    get queue() : Song[]{return this.musicQueueChange.value;}

    set queue(songs : Song[]){this.musicQueueChange.next(songs)}

    playMusicCollection(songs: Song[], startIndex : number){
        let history = [];
        this.historyChange.next(history);
        let list = songs.slice(startIndex, songs.length);
        this.queue = list;
    }

    playAlbum(album : Album, startIndex : number){
        let history = [];
        this.historyChange.next(history);
        let songs = album.songs.slice(startIndex, album.songs.length);
        this.queue = songs;
    }

    playPlaylist(playlist : Playlist, startIndex : number){
        let history = [];
        this.historyChange.next(history);
        let songs = playlist.songs.slice(startIndex, playlist.songs.length);
        this.queue = songs;
    }

    addSongToQueue(song : Song){
        let newQueue = this.queue.slice();
        newQueue.push(song);
        this.queue = newQueue;
    }

    addAlbumToQueue(album : Album){
        let temp = this.queue;

        for(let song of album.songs){
            temp.push(song);
        }
        this.queue = temp;
    }

    addPlaylistToQueue(playlist : Playlist){
        let temp = this.queue;

        for(let song of playlist.songs){
            temp.push(song);
        }
        this.queue = temp;
    }

    popFromQueue() : boolean{
        let last_song = this.queue[0];
        let queue = this.queue.slice();
        queue.shift();
        if(queue.length > 0){
            let history = this.history.slice();
            history.push(last_song);
            this.historyChange.next(history);
            this.queue = queue;
            return true;
        }
        else{
            return false;
        }

    }

    getRandomSong(){
        let index = this.randomIntFromInterval(0,this.queue.length-1);
        return this.queue[index];
    }

    private randomIntFromInterval(min,max){
        return Math.floor(Math.random()*(max-min+1)+min);
    }

    getPreviousSong()  : boolean{
        if(this.history.length > 0){
            let history = this.history.slice();
            
            let song = history.pop();
            this.historyChange.next(history);
            let queue = this.queue.slice();
            queue.unshift(song);
            this.queue = queue;
            return true;
        }
        else{
            return false;
        }
        
    }


    
}
