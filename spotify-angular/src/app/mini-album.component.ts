import { Component, Input } from '@angular/core';
import {PlayerComponent} from './player.component'
import {Song} from './objs/Song';

@Component({
  selector: 'mini-album',
  templateUrl: '../views/mini-album.component.html',
  styleUrls: ['../views/styles/album.component.css']
})
export class MiniAlbumComponent {
  @Input() songs : Song[];

}