import { Component, Input,Inject, OnInit } from '@angular/core';
import {RequestedAlbum} from './objs/RequestedAlbum';
import {Song} from './objs/Song';
import {User} from './objs/User';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {AdminService} from './services/admin.service';


@Component({
  selector: 'admin-page',
  templateUrl: '../views/admin.component.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class AdminComponent implements OnInit{

  constructor(public dialog: MdDialog,
              private adminService : AdminService){}

  requests     : RequestedAlbum[];
  users        : User[] = [];
  selectedUser : User;

  

  ngOnInit(){

    this.adminService.getRequestedAlbums().subscribe(
      (data) =>{
        this.requests = data
      },
      (err) =>{
        console.log("ERROR!");
      }
    );

  }

  chooseSelectedUser(user){
    this.selectedUser = user;
  }

  openDialog(row){
    let dialogRef = this.dialog.open(DetailDialog, {
      width: '500px',
      data: row
    });
  }
}

@Component({
  selector: 'detail-dialog',
  templateUrl: '../views/detail-dialog.html',
  styleUrls:["../views/styles/detail-dialog.css"]
})
export class DetailDialog {

  constructor(
    public dialogRef: MdDialogRef<DetailDialog>,
    @Inject(MD_DIALOG_DATA) public data: any) {
      console.log(data)
     }

  onNoClick(): void {
    this.dialogRef.close();
  }

}