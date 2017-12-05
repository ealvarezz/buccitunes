import { Component, Input,Inject, OnInit } from '@angular/core';
import {RequestedAlbum} from './objs/RequestedAlbum';
import {Song} from './objs/Song';
import {User} from './objs/User';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {AdminService} from './services/admin.service';
import {DetailDialog} from './detail-dialog.component';
import {NotificationsService} from 'angular4-notifications';
import {SpinnerService} from './services/spinner.service';


@Component({
  selector: 'requested-album',
  templateUrl: '../views/requested-albums.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class RequestedAlbumComponent implements OnInit{

  constructor(public dialog: MdDialog,
              private adminService : AdminService,
              private notificationService : NotificationsService,
              private spinnerService      : SpinnerService){}

  requests     : RequestedAlbum[];

  ngOnInit(){
    this.loadRequestedAlbums();
  }

  loadRequestedAlbums(){
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
    this.spinnerService.openSpinner();
    this.adminService.approveAlbum(album).subscribe(
      (data) =>{
        this.loadRequestedAlbums();
        let albumTitle = album.title;
        this.spinnerService.stopSpinner();
        this.notificationService.success("SUCCESS", "Album" +album.title+ "successfully added to the system.");
      },
      (err) =>{
        this.notificationService.error("FAIL", err);
      }
    )
  }

  rejectAlbum(album : RequestedAlbum){
    this.spinnerService.openSpinner();
    this.adminService.rejectAlbum(album).subscribe(
      (data) =>{
        this.loadRequestedAlbums();
        this.spinnerService.stopSpinner();
        let albumTitle = album.title;
        this.notificationService.success("SUCCESS", "Album has been rejected.");
      },
      (err) =>{
        this.notificationService.error("FAIL", err);
      }
    )
  }

  spinner(){
    this.spinnerService.openSpinner();
  }

  approveSelectedAlbums(){
    let albums = this.getSelectedAlbums();
    for(let album of albums){
      this.approveAlbum(album);
    }
  }

  rejectSelectedAlbums(){
    let albums = this.getSelectedAlbums();
    for(let album of albums){
      this.rejectAlbum(album);
    }
  }

  getSelectedAlbums() : RequestedAlbum[]{
    let albums = []
    for(let album of this.requests){
      if(album.accept) albums.push(album);
    }
    return albums;
  }

  openDialog(row){

    let dialogRef;

    this.adminService.getRequestedAlbum(row).subscribe(
      (data) =>{

        dialogRef = this.dialog.open(DetailDialog, {
          width: '800px',
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
