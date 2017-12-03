import { Component, Input,Inject, OnInit } from '@angular/core';
import {RequestedAlbum} from './objs/RequestedAlbum';
import {Song} from './objs/Song';
import {User} from './objs/User';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {AdminService} from './services/admin.service';
import {DetailDialog} from './detail-dialog.component';


@Component({
  selector: 'requested-songs',
  templateUrl: '../views/requested-songs.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class RequestedSongsComponent implements OnInit{

  constructor(public dialog: MdDialog,
              private adminService : AdminService){}

  requests     : RequestedAlbum[];

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

  openDialog(row){

    let dialogRef;

    this.adminService.getRequestedAlbum(row).subscribe(
      (data) =>{

        dialogRef = this.dialog.open(DetailDialog, {
          width: '500px',
          data: data
        });

      },
      (err) =>{
        console.log("error");
      }
    );

  }
}
