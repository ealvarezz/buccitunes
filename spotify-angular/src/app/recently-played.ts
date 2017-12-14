import { Component, OnInit } from '@angular/core';
import {MusicCollectionService} from './services/music.service';
import {Album} from './objs/Album';
import {AuthenticationService} from './services/authentication.service';
import {UserService} from './services/user.service';
import {MediaService} from './services/media.service';

@Component({
  selector: 'recent-albums',
  templateUrl: '../views/recently-played.html'
})
export class RecentlyPlayed implements OnInit {


  albums : Album[] = [];
  rowHeight = 300;
  

  constructor(private musicService : MusicCollectionService,
              private authService  : AuthenticationService,
              private userService  : UserService,
              private mediaService : MediaService) { }

  ngOnInit() {

    this.getUser(this.authService.currentUser.email);
    // this.authService.currentUserChange.subscribe(
    //   (user) => {
    //     // this.getUser(user.email);
    //   }
    // );
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
}