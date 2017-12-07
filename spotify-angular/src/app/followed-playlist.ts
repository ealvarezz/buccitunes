import { Component, OnInit } from '@angular/core';
import {MusicCollectionService} from './services/music.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {Playlist} from './objs/Playlist';
import {environment} from '../environments/environment';
import {AuthenticationService} from './services/authentication.service';
import {UserService} from './services/user.service';
import {MediaService} from './services/media.service';

@Component({
  selector: 'followed-playlists',
  templateUrl: '../views/followed-playlists.html'
})
export class FollowedPlaylists implements OnInit {


  playlists : Playlist[] = [];
  rowHeight = 300;
  

  constructor(private musicService : MusicCollectionService,
              private authService  : AuthenticationService,
              private userService  : UserService,
              private mediaService : MediaService) { }

  ngOnInit() {
      console.log("WTF MAN");
        this.authService.currentUserChange.subscribe(
            (user) => {
            this.getUser(user.email);
        }
    );
  }


  getUser(id : string){
    this.userService.getUser(id).subscribe(
      (data) => {
        this.playlists = data.user.followingPlaylists;
      },
      (err) =>{
        console.log(err);
      }
    );
  }

  getImageUrl(imagePath : string){
      return environment.SERVER_PATH + imagePath;
  }

}