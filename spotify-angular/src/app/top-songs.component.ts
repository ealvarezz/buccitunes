import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'
import {MusicCollectionService} from './services/music.service';
import {QueueService} from './services/queue.service';
import {MusicService} from './services/player.service';







@Component({
  selector: 'charts-page',
  templateUrl: '../views/top-songs.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class TopSongsComponent{

    constructor(private musicService    : MusicCollectionService,
                private playerService   : MusicService,
                private queueService    : QueueService){}

    @Input() songs : Song[];

    
    ngOnInit(){
        if(!this.songs){
            this.reloadPlaylist();
        }
    }


    playSongs(){
        this.queueService.playMusicCollection(this.songs, 0);
        this.playerService.playSong();
    }

    


   
    
    reloadPlaylist(){
        this.musicService.getTopCharts().subscribe(
            (data)=>{
                this.songs = data.topSongs;
            }
        )
    }


}
      


    



