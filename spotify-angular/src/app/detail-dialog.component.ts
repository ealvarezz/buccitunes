import { Component,Inject} from '@angular/core';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {environment} from '../environments/environment';

@Component({
  selector: 'detail-dialog',
  templateUrl: '../views/detail-dialog.html',
  styleUrls:["../views/styles/detail-dialog.css"]
})
export class DetailDialog{

  artworkPath : String;

  constructor(
    public dialogRef: MdDialogRef<DetailDialog>,
    @Inject(MD_DIALOG_DATA) public data: any) {
      console.log(data)
      this.artworkPath = environment.SERVER_PATH + data.artworkPath;
     }

  onNoClick(): void {
    this.dialogRef.close();
  }


  acceptAlbum(){
   this.dialogRef.close(this.data);
  }

}