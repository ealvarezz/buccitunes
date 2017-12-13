import { Component } from '@angular/core';
import {Song} from './objs/Song'
import {MusicCollectionService} from './services/music.service';
import {MediaService} from './services/media.service';
import {UserService} from './services/user.service';
import {TopCharts} from './objs/TopCharts'
import 'rxjs/add/observable/forkJoin';
import { Observable } from 'rxjs/Rx';
import {Activity} from './objs/Activity';

@Component({
  selector: 'home-page',
  templateUrl: '../views/homepage.component.html'
})
export class HomePageComponent {

  private songs : Song[];
  public  charts : TopCharts = new TopCharts();
  public activityFeed     : Activity[];
  rowHeight : number = 300; 


  genres = [ { img: "/assets/hip_hop.png", id: 1}, {img: "/assets/pop.png", id:3}, {img: "/assets/rnb.png", id:7}, {img: "/assets/rock.png", id: 11}, {img: "/assets/latin.png", id: 4}, {img:"/assets/metal.png", id:8}, {img: "/assets/indie.png", id: 15}] 



  constructor(private musicService : MusicCollectionService,
              private mediaService : MediaService,
              private userService : UserService){}

  ngOnInit(){

    Observable.forkJoin(this.musicService.getTopCharts(), this.userService.getActivityFeed()).subscribe(
      (data)=>{
        this.charts = data[0];
        this.activityFeed =  data[1];
      }
    )
    // this.musicService.getTopCharts().subscribe(
    //   (data)=>{
    //     this.charts = data;
    //   }
    // );

  }

  
}
