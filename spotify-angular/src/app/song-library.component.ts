import { Component, OnInit } from '@angular/core';
import {MusicCollectionService} from './services/music.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {Song} from './objs/Song';

@Component({
  selector: 'song-library',
  templateUrl: '../views/song-library.html'
})
export class SongLibrary implements OnInit {


  songs : Song[];
  

  constructor(private musicService : MusicCollectionService) { }

  ngOnInit() {
        this.getLibrary();
  }

  getLibrary(){
      this.musicService.getSongLibrary().subscribe(
          (data)=>{
            this.songs = data;
          },
          (err) =>{
            console.log(err);
          }
      )
  }

 



}
