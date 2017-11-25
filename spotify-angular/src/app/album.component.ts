import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'
import {Album} from './objs/Album'
// import {DomSanitizer} from '@angular/platform-browser';

import {MusicCollectionService} from './services/music.service';

import { ActivatedRoute, ParamMap } from '@angular/router';


@Component({
  selector: 'album-page',
  templateUrl: '../views/album.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class AlbumComponent{

    album : Album;
    artworkUrl : string;
    file_path : string = "http://localhost:8080"
    

    constructor(private route: ActivatedRoute, private musicService : MusicCollectionService){}


    ngOnInit(){
        this.route.params.subscribe(params => {
            this.getAlbum(+params['id']);
        });
    }

    getAlbum(id : number ){
    this.musicService.getAlbum(id)
                .subscribe(
                    (data) => {
                        this.album = data;
                        this.artworkUrl = this.file_path + this.album.artwork;
                    },
                    (err) => {
                        console.log(err.message);
                    });
  }
    

}