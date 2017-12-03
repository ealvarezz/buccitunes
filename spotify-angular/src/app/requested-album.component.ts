import { Component, Input,Inject, OnInit } from '@angular/core';
import {RequestedAlbum} from './objs/RequestedAlbum';
import {Song} from './objs/Song';
import {User} from './objs/User';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {AdminService} from './services/admin.service';
import {DetailDialog} from './detail-dialog.component';
import {NotificationsService} from 'angular4-notifications';


@Component({
  selector: 'requested-album',
  templateUrl: '../views/requested-albums.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class RequestedAlbumComponent implements OnInit{

  constructor(public dialog: MdDialog,
              private adminService : AdminService,
              private notificationService : NotificationsService){}

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

  approveAlbum(album : RequestedAlbum){
    this.adminService.approveAlbum(album).subscribe(
      (data) =>{
        this.notificationService.success("SUCCESS", "Album successfully added to the system.");
      },
      (err) =>{
        this.notificationService.error("FAIL", err);
      }
    )
  }

  openDialog(row){

    let dialogRef;

    this.adminService.getRequestedAlbum(row).subscribe(
      (data) =>{

        dialogRef = this.dialog.open(DetailDialog, {
          width: '500px',
          data: data
        });

      dialogRef.afterClosed().subscribe(result => {
        if(result){
          this.approveAlbum(result);
        }
    });

      },
      (err) =>{
        console.log("error");
      }
    );



  }
}
