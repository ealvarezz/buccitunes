import { Component } from '@angular/core';
import {MdDialog, MdDialogRef} from '@angular/material';
import {MusicCollectionService } from './services/music.service';
import {MediaService } from './services/media.service';
import {MediaFile} from './objs/MediaFile';
import {Playlist} from './objs/Playlist';
import {environment} from '../environments/environment';


@Component({
  selector: 'add-playlist',
  templateUrl: '../views/add-playlist.html',
  styleUrls:["../views/styles/add-playlist.css"]
})
export class AddPlaylistDialog {

  constructor(public dialogRef: MdDialogRef<AddPlaylistDialog>,
              private musicService : MusicCollectionService,
              public mediaService : MediaService ) {
     }

     playlistName : string;
     playlistArtwork : string = environment.DEFAULT_ARTWORK;


     closeDialog(){
         this.dialogRef.close();
     }

     addPlaylist() : void{
        let playlist = this.constructPlaylist();

        this.musicService.addPlaylist(playlist)
            .subscribe(
                (data : Playlist) => {
                    this.closeDialog();
                    console.log(data);
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

         return playlist;
     }

     uploadImage(event){
       this.mediaService.previewImage(event).subscribe(
           (data : MediaFile)=>{
                this.playlistArtwork = data.artwork;
           });
       
     }

}