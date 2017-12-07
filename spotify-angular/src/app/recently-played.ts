import { Component, OnInit } from '@angular/core';
import {MusicCollectionService} from './services/music.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {Album} from './objs/Album';
import {environment} from '../environments/environment';
import {AuthenticationService} from './services/authentication.service';
import {UserService} from './services/user.service';

@Component({
  selector: 'recent-albums',
  templateUrl: '../views/recently-played.html'
})
export class RecentlyPlayed implements OnInit {


  albums : Album[] = [];
  rowHeight = 300;
  

  constructor(private musicService : MusicCollectionService,
              private authService  : AuthenticationService,
              private userService  : UserService) { }

  ngOnInit() {
    this.authService.currentUserChange.subscribe(
      (user) => {
        this.getUser(user.email);
      }
    );
  }


  getUser(id : string){
    this.userService.getUser(id).subscribe(
      (data) => {
        this.albums = data.user.recentlyPlayed;
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