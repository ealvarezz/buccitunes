import { Component,Inject} from '@angular/core';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {environment} from '../environments/environment';


@Component({
  selector: 'confirm-dialog',
  templateUrl: '../views/confirm-dialog.component.html',
  styleUrls:["../views/styles/confirm-dialog.css"]
})
export class ConfirmDialog{

  type : string;
  constructor(
    public dialogRef: MdDialogRef<ConfirmDialog>,
    @Inject(MD_DIALOG_DATA) public data: any) {
        this.type = data;
     }

}