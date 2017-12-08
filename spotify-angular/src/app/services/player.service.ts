import { Injectable } from '@angular/core';
import { Song } from './../objs/Song';
import {BucciConstants} from '../../environments/app.config';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {environment} from '../../environments/environment';
import {QueueService} from './queue.service';
import {MusicCollectionService} from './music.service';

@Injectable()
export class MusicService {

    public audioChange      = new BehaviorSubject<HTMLAudioElement>(new Audio());
    public muteChange       =  new BehaviorSubject<boolean>(false);
    public pauseChange      = new BehaviorSubject<boolean>(false);
    public playingChange    = new BehaviorSubject<boolean>(false);
    public currTimeChange   = new BehaviorSubject<number>(0);
    public currSongChange   = new BehaviorSubject<any>(new Song());
    public volumeChange     = new BehaviorSubject<number>(100);
    public secretChange     = new BehaviorSubject<boolean>(false);
    public loopChange       = new BehaviorSubject<boolean>(false);
    public shuffleChange    = new BehaviorSubject<boolean>(false);


    set currTime(time: number) {
        this.audio.currentTime = (time* this.audio.duration)/100;
        this.currTimeChange.next(time);
    }

    get audio()         : HTMLAudioElement { return this.audioChange.value;}
    get muted()         : boolean { return this.muteChange.value; }
    get isPaused()      : boolean { return this.pauseChange.value }
    get isPlaying()     : boolean {return this.playingChange.value }
    get currTime()      : number {return this.currTimeChange.value }
    get currSong()      : Song { return this.currSongChange.value }
    get volume()        : number { return this.volumeChange.value }
    get secretMode()    : boolean { return this.secretChange.value }
    get loop()          : boolean {return this.loopChange.value}
    get shuffle()       : boolean {return this.shuffleChange.value}

    



    constructor(private queueService : QueueService, private musicCollectionService : MusicCollectionService){
        this.audio.ontimeupdate = this.updateTime.bind(this);
        this.audio.onended = this.onEndHandler.bind(this);
        
        this.queueService.musicQueueChange.subscribe(
            queue => this.currSongChange.next(queue[0])
        )
    }

    onEndHandler(){
        if(!this.loop){
            if(!this.shuffle){
                if(this.queueService.popFromQueue()){
                    this.playSong();
                }
            }
            else{
                this.playRandomSong();
            }

        }
        
    }
    setCurrentSong(song){
        this.currSongChange.next(song);
    }

    updateTime(){
        let time = (this.audio.currentTime / this.audio.duration)*100;
        this.currTimeChange.next(time)
    }

    toggleSecretMode(){
        this.secretChange.next(!this.secretMode);
    }

    toggleLoop(){
        this.audio.loop = !this.loop;
        this.loopChange.next(!this.loop);
    }

    toggleShuffle(){
        this.shuffleChange.next(!this.shuffle);
    }

    playSong(){
            if(this.queueService.queue.length > 0){
                this.play();
            }
    }

    play(){
        if(this.isPaused){
            this.audio.play();
        }
        else{
            this.audio.src = environment.SERVER_PATH+ this.currSong.audioPath;
            this.audio.load();
            this.audio.play();
            this.musicCollectionService.recordSong(this.currSong)
            .subscribe(data => console.log("song recorded"));
        }
        this.togglePlaying(true);
        
    }

    playSongEncoded(){
        if(this.isPaused){
            this.audio.play();
        }
        else{
            this.audio.src = this.currSong.audio;
            this.audio.load();
            this.audio.play();
        }
        this.togglePlaying(true);
        
    }


    skipNext(){
        if(this.shuffle){
            this.playRandomSong();
        }
        else if(this.queueService.popFromQueue()){
             this.playSong();
        }
    }

    skipPrevious(){
        if(!this.shuffle){
            if(this.queueService.getPreviousSong()){
                this.playSong();
            }
        }
    }

    playRandomSong(){
        let song = this.queueService.getRandomSong();
        this.currSongChange.next(song);
        this.playSong();
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