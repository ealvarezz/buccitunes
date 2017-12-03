import { Component, Input } from '@angular/core';
import {MusicService } from './services/player.service';
import { Observable } from 'rxjs/Observable';
import { Song } from './objs/Song';

@Component({
  selector: 'music-player',
  templateUrl: '../views/player.component.html',
  styleUrls: ['../views/styles/player.component.css']
})
export class PlayerComponent {
    private minVolume : number = 0;
    private maxVolume : number = 100;
    private volIncrement: number = 2;

    private isPaused : boolean
    private isPlaying : boolean
    private song : Song 
    private volume : number
    private time : number


    private secretMode : boolean;

    showBar : boolean = false;

    
    constructor(private musicService : MusicService){
    }

    ngOnInit(){
      this.musicService.volumeChange.subscribe(
          vol => this.volume = vol
      );
      this.musicService.currSongChange.subscribe(
          song => this.song = song
      );
      this.musicService.currTimeChange.subscribe(
          time => this.time = time
      );
      this.musicService.playingChange.subscribe(
          playing => this.isPlaying = playing
      );
      this.musicService.pauseChange.subscribe(
          pause => this.isPaused = pause
      );
    
      this.musicService.secretChange.subscribe(
          secret => this.secretMode = secret
      );
      
    }

    mute(){
      this.musicService.changeVolume(0);
    }

    toggleSecret(){
        this.musicService.toggleSecretMode();
    }

    changeVolume(){
       this.musicService.changeVolume(this.volume);
    }

     play(){
        this.musicService.playSong();
    }
    pause(){
        this.musicService.pauseSong();
    }

    hover(){
        this.showBar = true;
    }
    unHover(){
        this.showBar = false;
    }

    


}
