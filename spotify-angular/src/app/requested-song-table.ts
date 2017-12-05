import { Component, Input, Output, OnInit, OnChanges, SimpleChanges, EventEmitter } from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {RequestedSong} from './objs/RequestedSong';
import {MusicCollectionService } from './services/music.service';
import {MusicService} from './services/player.service';
import 'rxjs/add/observable/of';

@Component({
  selector: 'requested-song-table',
  templateUrl: '../views/requested-song-table.html'
})
export class RequestedSongTable implements OnChanges {
    @Input() data : RequestedSong[];
    dataSource : SongDataSource;
    hoveredRow : number = -1;
    displayedColumns = ['accept','num','songName','explicit','duration'];

    constructor(private musicService : MusicCollectionService,
                private playerService : MusicService){}

    ngOnChanges(change : SimpleChanges){
      if(change['data'] && !change['data'].isFirstChange()){
        this.data = change['data'].currentValue;
        this.dataSource.songs.next(this.data);
      }
    }

    playSong(song : RequestedSong){
      this.playerService.setCurrentSong(song);
      this.playerService.playSong();
    }

    ngOnInit(){
        this.dataSource = new SongDataSource(this.data);

    }

    hover(i){
      this.hoveredRow = i;
    }
    unHover(){
      this.hoveredRow = -1;
    }


      
      
}




export class SongDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  songs : BehaviorSubject<RequestedSong[]> = new BehaviorSubject([]);
  constructor(private data: RequestedSong[]){
      super()
      this.songs.next(data)
  }

  connect(): Observable<RequestedSong[]> {
    return this.songs;
  }

  disconnect() {}
}