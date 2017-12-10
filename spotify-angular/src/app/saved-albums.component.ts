import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {MusicCollectionService} from './services/music.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {Album} from './objs/Album';
import {environment} from '../environments/environment';
import {Observable} from 'rxjs/Observable';
import {SearchResults} from './objs/SearchResults';
import {PropertiesPipe} from './properties-pipe';

@Component({
  selector: 'saved-albums',
  templateUrl: '../views/saved-albums.component.html'
})
export class AlbumLibrary implements OnInit {


  albums : Album[];
  keys : string[];
  rowHeight = 300;
  
  constructor(private musicService : MusicCollectionService) { }

  ngOnInit() {
    this.getAlbumLibrary();
    
  }

  getImageUrl(imagePath : string){
      return environment.SERVER_PATH + imagePath;
  }

  getAlbumLibrary(){
      this.musicService.getAlbumLibrary().subscribe(
          (data)=>{
            this.albums = data;
          },
          (err) =>{
            console.log(err);
          }
      )
  }
}