import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {MusicCollectionService} from './services/music.service';
import {MediaService} from './services/media.service';
import {Router } from '@angular/router';
import {environment} from '../environments/environment';
import {Observable} from 'rxjs/Observable';
import {SearchResults} from './objs/SearchResults';
import {PropertiesPipe} from './properties-pipe';
import {Song} from './objs/Song';
import {Album} from './objs/Album';
import {Artist} from './objs/Artist';
import {Playlist} from './objs/Playlist';
import {Location} from '@angular/common';

@Component({
  selector: 'search',
  templateUrl: '../views/search.component.html',
  styleUrls: ['../views/styles/search.component.css']
})
export class SearchComponent implements OnInit{

 myControl = new FormControl();
filteredOptions: Observable<SearchResults>;

constructor(private musicService : MusicCollectionService,
            private router : Router,
            private location : Location,
            private mediaService : MediaService){}

labelMap = {
    "userResults" : "Users",
    "songResults" : "Songs",
    "artistResults" : "Artists",
    "albumResults" : "Albums",
    "playlistResults" : "Playlists"
}


ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges
      .debounceTime(500)
      .distinctUntilChanged()
      .switchMap(term => term ?
            this.musicService.search(term) : Observable.of([]) 
    );
}

goBack(){
    this.location.back();
}

goToSelection(value, group ){
    if(group ==="songResults"){
        let id = value.album.id;
        this.router.navigate(['/album', id]);
    }
    else if (group === "artistResults"){
        let id = value.id;
        this.router.navigate(['/artist', id]);
    }
    else if(group === "albumResults"){
        let id = value.id;
        this.router.navigate(['/album', id]);
    }
    else if (group === "playlistResults"){
        let id = value.id;
        this.router.navigate(['/playlist', id]);
    }
}

displayFn(value : any): string {
    if(!value){
        return '';
    }
    if(value['title']){
        return value['title'];
    }
    else{
        return value['name'];
    }
  }


}

