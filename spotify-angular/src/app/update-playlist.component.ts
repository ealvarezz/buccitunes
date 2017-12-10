import { Component, Inject, OnInit } from '@angular/core';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {MusicCollectionService } from './services/music.service';
import {MediaService } from './services/media.service';
import {MediaFile} from './objs/MediaFile';
import {Playlist} from './objs/Playlist';
import {environment} from '../environments/environment';
import {NotificationsService} from 'angular4-notifications';


@Component({
  selector: 'update-playlist',
  templateUrl: '../views/update-playlist.html',
  styleUrls:["../views/styles/add-playlist.css"]
})
export class UpdatePlaylistDialog implements OnInit {

  constructor(public dialogRef: MdDialogRef<UpdatePlaylistDialog>,
              @Inject(MD_DIALOG_DATA) public data: any,
              private musicService : MusicCollectionService,
              public mediaService : MediaService,
              private notificationService: NotificationsService) {
     }

     playlist : Playlist;
     
     ngOnInit(){
        let newPlaylist = new Playlist();
        newPlaylist.title = this.data.playlist.title;
        newPlaylist.id = this.data.playlist.id;
        newPlaylist.artworkPath = this.data.playlist.artworkPath
        newPlaylist.collaborative = this.data.playlist.collaborative;
        newPlaylist.public = this.data.playlist.public;
        newPlaylist.description = this.data.playlist.description;
        this.playlist = newPlaylist;
     }

     closeDialog(){
         this.dialogRef.close();
     }

     updatePlaylist() : void{
        if(this.playlist.artwork){
            this.playlist.artwork = this.mediaService.trimImageBase64(this.playlist.artwork);
        }
        this.dialogRef.close(this.playlist);

        // this.musicService.updatePlaylist(this.playlist)
        //     .subscribe(
        //         (data : Playlist) => {
        //             this.notificationService.success("SUCCESS", "This playlist has been added!");
        //             this.closeDialog();
        //         },
        //         (err) => {
        //             console.log(err.message);
        //         });
     }

     uploadImage(event){
       this.mediaService.previewImage(event).subscribe(
           (data : MediaFile)=>{
                this.playlist.artwork = data.artwork;
           });
       
     }

}
