import { Component, Input,Output, OnInit, OnChanges, SimpleChanges, EventEmitter, ViewChild} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Song} from './objs/Song';
import {Playlist} from './objs/Playlist';
import {MusicCollectionService } from './services/music.service';
import {MusicService} from './services/player.service'
import {QueueService} from './services/queue.service';
import 'rxjs/add/observable/of';
import {MdSort} from '@angular/material';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';


@Component({
  selector: 'music-table',
  templateUrl: '../views/music-table.component.html',
  styleUrls: ["../views/styles/music-table.component.css"]
})
export class MusicTableComponent implements OnChanges {
    @Input() data : Song[];
    sortedData : Song[];
    @Input() isAlbum;
    @Input() isRequest : boolean = false;
    @Input() isQueue : boolean = false;
    @Input() chart   : boolean = false;
    @Output() remove : EventEmitter<any> = new EventEmitter();
    @Input() saved : boolean = false;
    dataSource : MusicDataSource;
    hoveredRow : number = -1;
    displayedColumns = ['num','add', 'songName','artist','album', 'more','duration'];

    playlists : Playlist[];

    constructor(private musicService : MusicCollectionService,
                private playerService : MusicService,
                private queueService : QueueService){}

    @ViewChild(MdSort) sort: MdSort;

    ngOnChanges(change : SimpleChanges){
      if(change['data'] && !change['data'].isFirstChange()){
        this.data = change['data'].currentValue;
        this.sort.direction= "";
        this.sort.sortChange.next(null);
        this.dataSource.songs.next(this.data);
      }
    }

    ngOnInit(){
        if(this.isAlbum == null){
          this.isAlbum = true;
        }
        this.dataSource = new MusicDataSource(this.data, this.sort);
        this.musicService.getUserPlaylists().subscribe(
          (data)=>{
            this.playlists = data;
          }
        );

        this.dataSource.connect().subscribe(
          (data)=>{
            this.sortedData = data;
            console.log("change");
          }
        )
    }

    playSong(song : Song){
      // implement this for song request tooooo
      if(this.sortedData){
        let index = this.sortedData.indexOf(song);
        this.queueService.playMusicCollection(this.sortedData, index);
        this.playerService.playSong();
      }
      else{
        let index = this.data.indexOf(song);
        this.queueService.playMusicCollection(this.data, index);
        this.playerService.playSong();
      }
      
    }

    addSongToQueue(song : Song){
      this.queueService.addSongToQueue(song);
      console.log(this.queueService.queue);
    }

    removeSongFromQueue(song : Song){
      this.queueService.removeSongFromQueue(song);
    }

    hover(i){
      this.hoveredRow = i;
    }
    unHover(){
      this.hoveredRow = -1;
    }

    addSongToLibrary(song : Song){
      song.album = null;
      this.musicService.addSongToLibrary(song).
      subscribe(
        (data) => {
          song.saved = true;
        },
        (err) =>{

        }
      )
    }

    addSongToPlaylist(song: Song, playlist : Playlist){
      this.musicService.addSongToPlaylists(playlist, song).
      subscribe(
        (data) =>{
          console.log("success")
        },
        (err)=>{
          console.log("nope");
        }
      )
    }

    removeSongFromPlaylist(song : Song){
      this.remove.emit(song);
    }
    

      
      
      
}






export class MusicDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  songs : BehaviorSubject<Song[]> = new BehaviorSubject([]);
  constructor(private data: Song[], private _sort: MdSort){
      super()
      this.songs.next(data)
  }



  connect(): Observable<Song[]> {

    const displayDataChanges = [
      this.songs,
      this._sort.sortChange,
    ];

    return Observable.merge(...displayDataChanges).map(() => {
      return this.getSortedData();
    });
  }

  getSortedData(): Song[] {
    if(!this.songs.value){
      return [];
    }
    const data = this.songs.value.slice();
    if (!this._sort.active || this._sort.direction == '') { return data; }

    return data.sort((a, b) => {
      let propertyA: number|string = '';
      let propertyB: number|string = '';

         ['num','add', 'songName','artist','album', 'more','duration'];
      switch (this._sort.active) {
        case 'songName': [propertyA, propertyB] = [a.name, b.name]; break;
        case 'artist': [propertyA, propertyB] = [a.owner.name, b.owner.name]; break;
        case 'album': [propertyA, propertyB] = [a.album.title, b.album.title]; break;
        case 'duration': [propertyA, propertyB] = [a.duration, b.duration]; break;
      }

      let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
    });
  }

  disconnect() {}
}