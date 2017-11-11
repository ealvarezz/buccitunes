import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'


@Component({
  selector: 'playlist-page',
  templateUrl: '../views/playlist.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class PlaylistComponent{

    songs: Song[];
    
    ngOnInit(){
        this.songs = [new Song(), new Song(), new Song(), new Song()];
    }
    

}