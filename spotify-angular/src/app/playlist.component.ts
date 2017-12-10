import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'
import {Playlist} from './objs/Playlist'
import {MusicCollectionService} from './services/music.service';
import { ActivatedRoute, ParamMap, Router} from '@angular/router';
import {environment} from '../environments/environment';
import {Location} from '@angular/common';
import {MediaService} from './services/media.service';
import {NotificationsService} from 'angular4-notifications';
import {ConfirmDialog} from './confirm-dialog.component';
import {MdDialog, MdDialogRef} from '@angular/material';
import {UpdatePlaylistDialog} from './update-playlist.component';


@Component({
  selector: 'playlist-page',
  templateUrl: '../views/playlist.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class PlaylistComponent{

    constructor(private musicService    : MusicCollectionService,
                private mediaService    : MediaService,
                private route           : ActivatedRoute,
                private _location       : Location,
                private router          : Router,
                private notifications   : NotificationsService,
                public dialog           : MdDialog){}

    playlist : Playlist;
    artworkUrl: string;
    
    ngOnInit(){
        this.route.params.subscribe(params => {
            this.getPlaylist(+params['id']);
        });
    }

    openUpdateDialog(){
    let dialogRef = this.dialog.open(UpdatePlaylistDialog, 
      {
        width: "700px" ,
        data: {
          playlist  : this.playlist,
        }
      });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.updatePlaylist(result);
      }

    });
    }

    updatePlaylist(playlist : Playlist){
        this.musicService.updatePlaylist(playlist)
            .subscribe(
                (data) => {
                    this.getPlaylist(this.playlist.id);
                },
                (err) => {
                    this.notifications.error("ERROR", "There was an error updating this playlist.");
                });
    }

    getPlaylist(id : number ){
    this.musicService.getPlaylist(id)
                .subscribe(
                    (data) => {
                        this.playlist = data;
                        this.playlist.artworkPath = this.playlist.artworkPath +'?d='+new Date().getTime();
                        // this.artworkUrl = this.playlist.artworkPath ? environment.SERVER_PATH + this.playlist.artworkPath : null;
                    },
                    (err) => {
                        this._location.back();
                        this.notifications.error("Unable to load album", "There was a problem loading this playlist.")
                        
                    });
   }

   followPlaylist(){
       this.musicService.followPlaylist(this.playlist)
       .subscribe(
           (data)=>{
               this.notifications.success("Success", "Successfully following playlist");
           },
           (err)=>{
                console.log("error");
            }
       )
   }

    deletePlaylist(){
        this.dialog.open(ConfirmDialog, {data: "Playlist",}).afterClosed().subscribe(result => {
            if(result){
                this.sendDeletePlaylist();
            }
        });
    }

    deleteSongFromPlaylist(song : Song){
        this.musicService.removeSongFromPlaylists(this.playlist, song).
        subscribe(
            (data) =>{
            console.log("success")
            this.reloadPlaylist();
            },
            (err)=>{
            console.log("nope");
            }
        );
        }
    
    reloadPlaylist(){
        this.getPlaylist(this.playlist.id);
    }


    private sendDeletePlaylist(){
         this.musicService.deletePlaylist(this.playlist)
        .subscribe(
           (data) =>{
               this.router.navigate(['/']);
               this.notifications.success("SUCCESS", "Playlist successfully deleted");
           },
           (err) =>{
               this.notifications.error("ERROR", "There was an issue deleting your playlist. Please try again later.");
           }
       );
   }

}
      


    



