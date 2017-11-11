import { Component } from '@angular/core';
import {PlayerComponent} from './player.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  private audio: any;


  playMusic(){
    this.audio = new Audio();
    this.audio.src = "../assets/sample.mp3";
    this.audio.load();
    this.audio.play();
    this.audio.currentTime = 3;

  }
}
