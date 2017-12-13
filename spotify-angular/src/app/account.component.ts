import { Component, ElementRef, ViewChild } from '@angular/core';
import { User } from './objs/User';
import {FormControl} from '@angular/forms';
import {Album} from './objs/Album';
import {UserPage} from './objs/UserPage';
import {UserService} from './services/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {MediaService} from './services/media.service';
import {NotificationsService} from 'angular4-notifications';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/observable/fromEvent';



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
  filterInput = new FormControl();
  followers : User[] = [];

  OVERVIEW_TAB        : number = 0;
  RECENTLY_PLAYED_TAB : number = 1;
  PUBLIC_PLAYLIST_TAB  : number = 2;


  ngOnInit(){
        this.route.params.subscribe(params => {
            this.getUser(params['id']);
        });


        this.filterInput.valueChanges
        .debounceTime(150)
        .distinctUntilChanged()
        .subscribe((val) => {
            this.followers = this.filterFriends(val);
        });
    }

    filterFriends(val) : User[]{
      return this.user.following.slice().filter((item: User) => {
        let searchStr = (item.email + item.username).toLowerCase();
        return searchStr.indexOf(val.toLowerCase()) != -1;
      });
    }


  getUser(id : string){
    this.userService.getUser(id).subscribe(
      (data : UserPage) => {
        this.user = data.user;
        this.user.isfollowing = data.isAFollower;
        this.getOverviewItems();
        this.followers = this.user.following;
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

  unfollowUser(){
    this.userService.unfollowUser(this.user).subscribe(
      (data)=>{
        this.user.isfollowing = false;
      },
      (err) =>{
        this.notificationService.error("ERROR",err);
      }
    );
  }

  getOverviewItems(){
    if(this.user.recentlyPlayed && this.user.recentlyPlayed.length > 0){
      this.overviewRecentlyPlayed = this.user.recentlyPlayed.slice(0,6);
    }

  }

}