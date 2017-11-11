import { Component } from '@angular/core';
import {MusicService } from './services/player.service'

@Component({
  selector: 'main-page',
  templateUrl: '../views/main.component.html'
})
export class MainComponent {


  constructor(private musicService : MusicService){
    }

}
