import { Component, Input,Inject } from '@angular/core';
import {Request} from './objs/Request';
import {Song} from './objs/Song';
import {User} from './objs/User';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';


@Component({
  selector: 'album-page',
  templateUrl: '../views/admin.component.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class AdminComponent{
  constructor(public dialog: MdDialog){

  }
  requests : Request[] = [new Request(new Song()), new Request(new Song()), new Request(new Song()), new Request(new Song()), new Request(new Song())]
  users : User[] = [new User(), new User(), new User(), new User()];
  selectedUser : User = this.users[0];

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