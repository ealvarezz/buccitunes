import { Component, Input,Inject, OnInit } from '@angular/core';
import { Router} from '@angular/router';





@Component({
  selector: 'admin-page',
  templateUrl: '../views/admin.component.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class AdminComponent implements OnInit{

  constructor(private router: Router){}


  ngOnInit(){
    this.router.navigate(['/admin/requested_albums']);
  }

  // requests     : RequestedAlbum[];
  // users        : User[] = [];
  // selectedUser : User;

  // chooseSelectedUser(user){
  //   this.selectedUser = user;
  // }

  
}
