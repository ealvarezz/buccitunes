import { Component, Inject } from "@angular/core";
import { MdDialog, MdDialogRef, MD_DIALOG_DATA } from "@angular/material";
import { FormControl, Validators } from "@angular/forms";

@Component({
  selector: "ad-dialog",
  templateUrl: "../views/ad-dialog.html"
})
export class AdDialog {
  constructor(
    public dialogRef: MdDialogRef<AdDialog>,
    @Inject(MD_DIALOG_DATA) public data: any
  ) {}

  imageUrl = "/assets/ad1.jpg";
  ngOnInit() {
    
  }
}
