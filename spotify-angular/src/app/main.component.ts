import { Component } from '@angular/core';
import {MusicCollectionService } from './services/music.service'

@Component({
  selector: 'main-page',
  templateUrl: '../views/main.component.html'
})
export class MainComponent {


  constructor(private musicService : MusicCollectionService){
    }

    ngOnInit(){
      this.getPlaylists();
    }

    getPlaylists(){
        this.musicService.getUserPlaylists().subscribe(
            (data) =>{
                console.log("updated playlist");
            }
        )
    }



}
