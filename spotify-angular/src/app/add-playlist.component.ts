import { Component } from '@angular/core';
import {MdDialog, MdDialogRef} from '@angular/material';
import {MusicCollectionService } from './services/music.service';
import {Playlist} from './objs/Playlist';


@Component({
  selector: 'add-playlist',
  templateUrl: '../views/add-playlist.html',
  styleUrls:["../views/styles/add-playlist.css"]
})
export class AddPlaylistDialog {

  constructor(public dialogRef: MdDialogRef<AddPlaylistDialog>, private musicService : MusicCollectionService) {
     }

     playlistName : string;

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

         return playlist;
     }
}