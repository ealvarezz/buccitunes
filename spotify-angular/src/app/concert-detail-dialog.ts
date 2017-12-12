import { Component, Inject } from "@angular/core";
import {Concert } from './objs/Concert';
import { MdDialog, MdDialogRef, MD_DIALOG_DATA } from "@angular/material";


@Component({
  selector: "concert-detail",
  templateUrl: "../views/concert-detail-dialog.html"
})
export class ConcertDetailComponent {
  constructor(
    public dialogRef: MdDialogRef<ConcertDetailComponent>,
    @Inject(MD_DIALOG_DATA) public data: any
  ) {}

  concert : Concert;
  ngOnInit() {
    this.concert = this.data.concert;
  }
}
