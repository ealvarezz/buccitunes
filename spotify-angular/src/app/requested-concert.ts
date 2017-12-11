import { Component, Input,Inject, OnInit } from '@angular/core';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {AdminService} from './services/admin.service';
import {Concert} from './objs/Concert'
import 'rxjs/add/observable/forkJoin';
import { Observable } from 'rxjs/Rx';
import {NotificationsService} from 'angular4-notifications';
import {SpinnerService} from './services/spinner.service';


@Component({
  selector: 'requested-concerts',
  templateUrl: '../views/requested-concerts.html',
  styleUrls: ["../views/styles/admin.component.css"]
})
export class RequestedConcertComponent implements OnInit{

  constructor(public dialog               : MdDialog,
              private adminService        : AdminService,
              private notificationService : NotificationsService,
              private spinnerService      : SpinnerService){}

  requests     : Concert[];

  ngOnInit(){
    this.loadRequestedConcerts();
  }

  loadRequestedConcerts(){
    this.adminService.getRequestedConcerts().subscribe(
      (data) =>{
        this.requests = data
      },
      (err) =>{
        console.log("ERROR!");
      }
    );
  }


  approveSelectedConcerts(){
    this.spinnerService.openSpinner();
    let selectedConcerts = this.getSelectedConcerts();
    let observables = [];
    for(let concert of selectedConcerts){
      observables.push(this.adminService.approveRequestedConcert(concert));
    }

    Observable.forkJoin(observables).subscribe(
      (data)=>{
        this.notificationService.success("SUCCESS", "Successfully approved the selected concerts");
        this.loadRequestedConcerts();
        this.spinnerService.stopSpinner();
      },
      (err)=>{
        this.notificationService.error("ERROR", "There was an issue approving one or more selected concerts");
      }
    );

  }

  rejectSelectedConcerts(){
    let selectedConcerts = this.getSelectedConcerts();
    let observables = [];
    for(let concert of selectedConcerts){
      observables.push(this.adminService.rejectRequestedConcert(concert));
    }

    Observable.forkJoin(observables).subscribe(
      (data)=>{
        this.notificationService.success("SUCCESS", "Successfully rejected the selected concerts");
      },
      (err)=>{
        this.notificationService.error("ERROR", "There was an issue rejecting one or more selected concerts");
      }
    )
  }

  getSelectedConcerts() : Concert[]{
    let selected = [];
    for(let request of this.requests){
      if(request.accept){
        selected.push(request);
      }
    }
    return selected;
  }





  
}
