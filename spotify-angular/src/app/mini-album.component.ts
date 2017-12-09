import { Component, Input, OnInit } from '@angular/core';
import {PlayerComponent} from './player.component'
import {Song} from './objs/Song';
import {Album} from './objs/Album';
import {MusicService} from './services/player.service';
import {QueueService} from './services/queue.service';
import {MusicCollectionService} from './services/music.service';
import {MediaService} from './services/media.service';

@Component({
  selector: 'mini-album',
  templateUrl: '../views/mini-album.component.html',
  styleUrls: ['../views/styles/album.component.css']
})
export class MiniAlbumComponent implements OnInit {
  @Input() album : Album;

  constructor(private playerService : MusicService,
              private queueService : QueueService,
              private musicService : MusicCollectionService,
              private mediaService : MediaService){}

  ngOnInit(){
      
  }

  playAlbum(){
      this.queueService.playMusicCollection(this.album.songs, 0);
      this.playerService.playSong();
  }

  saveAlbum(){
    this.musicService.saveAlbum(this.album).subscribe(
           (data) =>{
              console.log("album saved");

           }
       );
  }

}