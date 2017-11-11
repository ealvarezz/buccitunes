import { Injectable } from '@angular/core';
import { Song } from './../objs/Song';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class MusicService {

    public audioChange = new BehaviorSubject<HTMLAudioElement>(new Audio());
    public muteChange =  new BehaviorSubject<boolean>(false);
    public pauseChange = new BehaviorSubject<boolean>(false);
    public playingChange = new BehaviorSubject<boolean>(false);
    public currTimeChange = new BehaviorSubject<number>(0);
    public currSongChange = new BehaviorSubject<Song>(new Song());
    public volumeChange = new BehaviorSubject<number>(100);


    get audio() : HTMLAudioElement { return this.audioChange.value;}
    get muted() : boolean { return this.muteChange.value; }
    get isPaused() : boolean { return this.pauseChange.value }
    get isPlaying() : boolean {return this.playingChange.value }
    get currTime() : number {return this.currTimeChange.value }
    get currSong() : Song { return this.currSongChange.value }
    get volume() : number { return this.volumeChange.value }


    constructor(){
        this.audio.ontimeupdate = this.updateTime.bind(this);
    }

    

    updateTime(){
        let time = (this.audio.currentTime / this.audio.duration)*100;
        this.currTimeChange.next(time)
    }

    playSong(){
        if(this.isPaused){
            this.audio.play();
        }
        else{
            this.audio.src = this.currSong.song_file;
            this.audio.load();
            this.audio.play();
        }
        this.togglePlaying(true);
        
    }


    pauseSong(){
        this.audio.pause();
        this.togglePlaying(false);
        
    }

    changeVolume(vol){
        this.audio.volume = vol/100;
        this.volumeChange.next(vol);
        let mute = (vol == 0);
        this.muteChange.next(mute);
    }

    private togglePlaying(isPlaying){
        this.audioChange.next(this.audio);
        this.playingChange.next(isPlaying);
        this.pauseChange.next(!isPlaying);
    }
   


    

}