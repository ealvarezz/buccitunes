import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'


@Component({
  selector: 'album-page',
  templateUrl: '../views/album.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class AlbumComponent{

    songs: Song[];
    
    ngOnInit(){
        this.songs = [new Song(), new Song(), new Song(), new Song()];
    }
    

}