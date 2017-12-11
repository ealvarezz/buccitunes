import { Component } from '@angular/core';
import {MdDialog, MdDialogRef} from '@angular/material';
import {Concert} from './objs/Concert';
import {Artist} from './objs/Artist';


@Component({
  selector: 'add-playlist',
  templateUrl: '../views/add-concert.html'
})
export class AddConcertDialog {
    concert : Concert;
    featuredArtists : Artist[] = [];

  constructor(public dialogRef: MdDialogRef<AddConcertDialog>) {
      this.concert = new Concert();
     }
    

     closeDialog(){
         this.dialogRef.close();
     }

     submitConcert(){
         this.dialogRef.close(this.concert);
     }

    


}
