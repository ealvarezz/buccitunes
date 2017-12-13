import { Component, OnInit } from '@angular/core';
import {MusicCollectionService} from './services/music.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {Song} from './objs/Song';
import {MusicService} from './services/player.service';
import {QueueService} from './services/queue.service';
import { Observable } from 'rxjs/Rx';

@Component({
  selector: 'song-library',
  templateUrl: '../views/song-library.html'
})
export class SongLibrary implements OnInit {


  songs : Song[];
  

  constructor(private musicService : MusicCollectionService,
              private queueService : QueueService,
              private playerService : MusicService) { }

  ngOnInit() {
        this.getLibrary();
  }

  getLibrary(){
      this.musicService.getSongLibrary().subscribe(
          (data)=>{
            this.songs = data;
          },
          (err) =>{
            console.log(err);
          }
      )
  }
  
  playLibrary(){
    this.queueService.playMusicCollection(this.songs, 0);
    this.playerService.playSong();
  }

  unsave(song : Song){
   this.musicService.unsaveSong(song).subscribe(
     (data)=>{
       this.getLibrary();
     }
   )
  }

 



}
