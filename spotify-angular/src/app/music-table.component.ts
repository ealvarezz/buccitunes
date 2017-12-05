import { Component, Input,Output, OnInit, OnChanges, SimpleChanges, EventEmitter} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Song} from './objs/Song';
import {Playlist} from './objs/Playlist';
import {MusicCollectionService } from './services/music.service';
import 'rxjs/add/observable/of';

@Component({
  selector: 'music-table',
  templateUrl: '../views/music-table.component.html',
  styleUrls: ["../views/styles/music-table.component.css"]
})
export class MusicTableComponent implements OnChanges {
    @Input() data : Song[];
    @Input() isAlbum;
    @Output() remove : EventEmitter<any> = new EventEmitter();
    dataSource : MusicDataSource;
    hoveredRow : number = -1;
    displayedColumns = ['num','add', 'songName','artist','album', 'more','duration'];

    playlists : Playlist[];

    constructor(private musicService : MusicCollectionService){}

    ngOnChanges(change : SimpleChanges){
      if(change['data'] && !change['data'].isFirstChange()){
        this.data = change['data'].currentValue;
        this.dataSource.songs.next(this.data);
      }
    }

    ngOnInit(){
        if(this.isAlbum == null){
          this.isAlbum = true;
        }
        this.dataSource = new MusicDataSource(this.data);

        this.musicService.getUserPlaylists().subscribe(
          (data)=>{
            this.playlists = data;
          }
        )
        // this.musicService.userPlaylists.subscribe(
        //   playlists => this.playlists = playlists
        // );

    }

    hover(i){
      this.hoveredRow = i;
    }
    unHover(){
      this.hoveredRow = -1;
    }

    addSongToLibrary(song : Song){
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
  constructor(private data: Song[]){
      super()
      this.songs.next(data)
  }

  connect(): Observable<Song[]> {
    return this.songs;
  }

  disconnect() {}
}