import { Component, Inject, OnInit } from '@angular/core';
import {MusicCollectionService} from './services/music.service';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'lyrics',
  templateUrl: '../views/lyrics.component.html'
})
export class LyricsComponent implements OnInit {

    constructor(private musicService : MusicCollectionService,
                @Inject(MD_DIALOG_DATA) public data: any){}
    
    lyrics : String;
    songName : String;
    
    ngOnInit(){
        this.lyrics = this.data.lyrics;
        this.songName = this.data.songName;
    }


}