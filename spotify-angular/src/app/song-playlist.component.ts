import { Component,Inject} from '@angular/core';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {environment} from '../environments/environment';
import {Song} from './objs/Song';

@Component({
  selector: 'song-playlist-dialog',
  templateUrl: '../views/song-playlist-dialog.html',
//   styleUrls:["../views/styles/detail-dialog.css"]
})
export class SongPlaylistDialog{


  constructor(
    public dialogRef: MdDialogRef<SongPlaylistDialog>,
    @Inject(MD_DIALOG_DATA) public data: Song) {
      console.log(data)
     }

  onNoClick(): void {
    this.dialogRef.close();
  }


  addSong(){
   this.dialogRef.close(this.data);
  }

}