import { Component, Input,Inject, OnInit } from '@angular/core';
import {UserService} from './services/user.service';
import {User} from './objs/User';
import { Router} from '@angular/router';
import {NotificationsService} from 'angular4-notifications';

@Component({
  selector: 'admin-page',
  templateUrl: '../views/user-dashboard.component.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class UserDashboard implements OnInit{

  users : User[] = [];
  selectedUser : User;

  constructor(private router: Router,
              private userService : UserService,
              private notificationService : NotificationsService){}


  ngOnInit(){
    this.userService.getAllUser().subscribe(
        (data)=>{
            this.users = data;
        },
        (err)=>{
            this.notificationService.error("ERROR", "Error retreiving users");
        }
    )
  }

chooseSelectedUser(user){
    this.selectedUser = user;
  }


  
}
