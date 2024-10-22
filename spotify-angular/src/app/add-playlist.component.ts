import { Component } from '@angular/core';
import {MdDialog, MdDialogRef} from '@angular/material';
import {MusicCollectionService } from './services/music.service';
import {MediaService } from './services/media.service';
import {MediaFile} from './objs/MediaFile';
import {Playlist} from './objs/Playlist';
import {environment} from '../environments/environment';
import {NotificationsService} from 'angular4-notifications';


@Component({
  selector: 'add-playlist',
  templateUrl: '../views/add-playlist.html',
  styleUrls:["../views/styles/add-playlist.css"]
})
export class AddPlaylistDialog {

  constructor(public dialogRef: MdDialogRef<AddPlaylistDialog>,
              private musicService : MusicCollectionService,
              public mediaService : MediaService,
              private notificationService: NotificationsService) {
     }
     

     playlistName : string;
     playlistArtwork : string = environment.LOCAL_RESOURCE + environment.DEFAULT_ARTWORK;
     playlistDescription : string;


     closeDialog(){
         this.dialogRef.close();
     }

     addPlaylist() : void{
        let playlist = this.constructPlaylist();

        this.musicService.addPlaylist(playlist)
            .subscribe(
                (data : Playlist) => {
                    this.notificationService.success("SUCCESS", "This playlist has been added!");
                    this.closeDialog();
                },
                (err) => {
                    console.log(err.message);
                });
     }
    
     constructPlaylist() : Playlist{
         let playlist   = new Playlist();
         playlist.title = this.playlistName;
         playlist.public = false;
         playlist.collaborative = false;
         playlist.artwork = this.playlistArtwork.split(',')[1];
         playlist.description = this.playlistDescription;
         return playlist;
     }

     uploadImage(event){
       this.mediaService.previewImage(event).subscribe(
           (data : MediaFile)=>{
                this.playlistArtwork = data.artwork;
           });
       
     }

}
