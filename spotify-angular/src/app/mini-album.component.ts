import { Component, Input, OnInit } from '@angular/core';
import {PlayerComponent} from './player.component'
import {Song} from './objs/Song';
import {Album} from './objs/Album';

@Component({
  selector: 'mini-album',
  templateUrl: '../views/mini-album.component.html',
  styleUrls: ['../views/styles/album.component.css']
})
export class MiniAlbumComponent implements OnInit {
  @Input() album : Album;
  albumArtworkUrl : string;
  file_path : string = "http://localhost:8080"

  ngOnInit(){
      this.albumArtworkUrl = this.file_path + this.album.artwork;
  }


}