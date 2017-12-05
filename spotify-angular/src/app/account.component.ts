import { Component } from '@angular/core';
import { User } from './objs/User';
import {Album} from './objs/Album';
import {UserPage} from './objs/UserPage';
import {UserService} from './services/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {MediaService} from './services/media.service';
import {NotificationsService} from 'angular4-notifications';


@Component({
  selector: 'account',
  templateUrl: '../views/account.component.html'
})
export class AccountComponent {

  constructor(private route           : ActivatedRoute,
              private userService     : UserService,
              private mediaService    : MediaService,
              private notificationService : NotificationsService){}

  user : User;
  overviewRecentlyPlayed : Album[];
  selectedTab : number;

  OVERVIEW_TAB        : number = 0;
  RECENTLY_PLAYED_TAB : number = 1;
  PUBLIC_PLAYLIST_TAB  : number = 2;


  ngOnInit(){
        this.route.params.subscribe(params => {
            this.getUser(params['id']);
        });
    }


  getUser(id : string){
    this.userService.getUser(id).subscribe(
      (data : UserPage) => {
        this.user = data.user;
        this.user.isfollowing = data.isAFollower;
        this.getOverviewItems();
      },
      (err) =>{
        console.log(err);
      }
    );
  }

  changeTab(index : number){
    this.selectedTab = index;
  }

  followUser(){
    this.userService.followUser(this.user).subscribe(
      (data)=>{
        this.user.isfollowing = true;
      },
      (err) =>{
        this.notificationService.error("ERROR",err);
      }
    )
  }

  getOverviewItems(){
    if(this.user.recentlyPlayed && this.user.recentlyPlayed.length > 0){
      this.overviewRecentlyPlayed = this.user.recentlyPlayed.slice(0,6);
    }

  }

}