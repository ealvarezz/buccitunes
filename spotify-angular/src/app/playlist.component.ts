import { Component, Input } from '@angular/core';
import {Song} from './objs/Song'
import {Playlist} from './objs/Playlist'
import {MusicCollectionService} from './services/music.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {environment} from '../environments/environment';
import {Location} from '@angular/common';
import {NotificationsService} from 'angular4-notifications';


@Component({
  selector: 'playlist-page',
  templateUrl: '../views/playlist.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class PlaylistComponent{

    constructor(private musicService : MusicCollectionService,
                private route           : ActivatedRoute,
                private _location       : Location,
                private notifications   : NotificationsService){}

    playlist : Playlist;
    artworkUrl: string;
    
    ngOnInit(){
        this.route.params.subscribe(params => {
            this.getPlaylist(+params['id']);
        });
    }

    getPlaylist(id : number ){
    this.musicService.getPlaylist(id)
                .subscribe(
                    (data) => {
                        this.playlist = data;
                        this.artworkUrl = environment.SERVER_PATH + this.playlist.artwork;
                    },
                    (err) => {
                        this._location.back();
                        this.notifications.error("Unable to load album", "There was a problem loading this playlist.")
                        
                    });
   }


    

}