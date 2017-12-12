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


  genres = [ { img: "/assets/hip_hop.png", id: 1}, {img: "/assets/pop.png", id:3}, {img: "/assets/rnb.png", id:7}, {img: "/assets/rock.png", id: 11}, {img: "/assets/latin.png", id: 4}, {img:"/assets/metal.png", id:8}, {img: "/assets/indie.png", id: 15}] 



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
