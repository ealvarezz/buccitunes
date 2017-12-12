import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'
import {Album} from './objs/Album'
import {Location} from '@angular/common';
import {MusicCollectionService} from './services/music.service';
import {NotificationsService} from 'angular4-notifications';
import {MediaService} from './services/media.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {QueueService} from './services/queue.service';
import {MusicService} from './services/player.service';


@Component({
  selector: 'album-page',
  templateUrl: '../views/album.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class AlbumComponent{

    album : Album;
    artworkUrl : string;
    file_path : string = "http://localhost:8080"
    

    constructor(private route           : ActivatedRoute,
                private musicService    : MusicCollectionService,
                private mediaService    : MediaService, 
                private _location       : Location,
                private notifications   : NotificationsService,
                private queueService    : QueueService,
                private playerService   : MusicService){}


    ngOnInit(){
        this.route.params.subscribe(params => {
            this.getAlbum(+params['id']);
        });
    }

    getAlbum(id : number ){
    this.musicService.getAlbum(id)
                .subscribe(
                    (data) => {
                        this.album = data;
                    },
                    (err) => {
                        this._location.back();
                        this.notifications.error("Unable to load album", "There was a problem loading this album.")
                        
                    });
   }

   saveAlbum(){
       this.musicService.saveAlbum(this.album).subscribe(
           (data) =>{
                this.album.following = true;
           }
       );
   }

   unSaveAlbum(){
    this.musicService.unSaveAlbum(this.album).subscribe(
           (data) =>{
            
                this.album.following = false;
           }
       );
   }

   playAlbum(){
        this.queueService.playMusicCollection(this.album.songs, 0);
        this.playerService.playSong();
   }
    
    

}