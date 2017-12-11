import { Component, OnInit } from '@angular/core';
import {Artist} from './objs/Artist';
import {AuthenticationService} from './services/authentication.service';
import {UserService} from './services/user.service';
import {MediaService} from './services/media.service';

@Component({
  selector: 'followed-artists',
  templateUrl: '../views/followed-artists.html'
})
export class FollowedArtists implements OnInit {


  artists : Artist[];
  rowHeight = 300;
  

  constructor(private authService  : AuthenticationService,
              private userService  : UserService,
              private mediaService : MediaService) { }

  ngOnInit() {
    this.getFollowedArtists();
  }


  getFollowedArtists(){
    this.userService.getFollowedArtists().subscribe(
      (data) => {
        this.artists = data;
      },
      (err) =>{
        console.log(err);
      }
    );
  }

}