import { Component } from '@angular/core';
import {Song} from './objs/Song'
import {MusicCollectionService} from './services/music.service';
import {MediaService} from './services/media.service';
import {TopCharts} from './objs/TopCharts'

@Component({
  selector: 'home-page',
  templateUrl: '../views/homepage.component.html'
})
export class HomePageComponent {

  private songs : Song[];
  private charts : TopCharts = new TopCharts();
  rowHeight : number = 300; 


  constructor(private musicService : MusicCollectionService,
              private mediaService : MediaService){}

  ngOnInit(){
    this.musicService.getTopCharts().subscribe(
      (data)=>{
        this.charts = data;
      }
    );

  }
}
