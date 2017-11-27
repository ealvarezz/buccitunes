import { Component } from '@angular/core';
import {PlayerComponent} from './player.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  public options = {
    position: ["bottom", "right"],
    timeOut: 5000,
    showProgressBar: true,

  }
}
