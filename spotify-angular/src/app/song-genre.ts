import { Component, Input } from '@angular/core';
import {Song, Genres} from './objs/Song'
import {MusicCollectionService} from './services/music.service';
import {QueueService} from './services/queue.service';
import {MusicService} from './services/player.service';
import { ActivatedRoute, ParamMap } from '@angular/router';


@Component({
  selector: 'genre-page',
  templateUrl: '../views/song-genre.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class GenreComponent{

    constructor(private musicService    : MusicCollectionService,
                private playerService   : MusicService,
                private queueService    : QueueService,
                private route            : ActivatedRoute,){}

    @Input() songs : Song[];
    genreName : string;

    
    ngOnInit(){
        this.route.params.subscribe(params => {
            let id = +params['id'];
            this.loadSongs(id);
            this.genreName = Genres[id];
        });
    }


    playSongs(){
        this.queueService.playMusicCollection(this.songs, 0);
        this.playerService.playSong();
    }

    loadSongs(id : number){
        this.musicService.getSongsByGenre(id).subscribe(
            (data)=>{
                this.songs = data;
            }
        )
    }


}
      


    



