import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'
import {MusicCollectionService} from './services/music.service';







@Component({
  selector: 'charts-page',
  templateUrl: '../views/top-songs.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class TopSongsComponent{

    constructor(private musicService    : MusicCollectionService){}

    @Input() songs : Song[];

    
    ngOnInit(){
        if(!this.songs){
            this.reloadPlaylist();
        }
    }

    


   
    
    reloadPlaylist(){
        this.musicService.getTopCharts().subscribe(
            (data)=>{
                this.songs = data.topSongs;
            }
        )
    }


}
      


    



