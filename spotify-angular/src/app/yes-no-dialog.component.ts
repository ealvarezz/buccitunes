import { Component, Inject } from "@angular/core";
import { MdDialog, MdDialogRef, MD_DIALOG_DATA } from "@angular/material";

@Component({
  selector: "yes-no-dialog",
  templateUrl: "../views/yes-no-dialog.component.html"
})
export class YesNoDialogComponent {
  constructor(
    public dialogRef: MdDialogRef<YesNoDialogComponent>,
    @Inject(MD_DIALOG_DATA) public data: any
  ) {}

  dialogHeader: string;
  dialogQuestion: string;
  yesStrBtn: String;
  noStrBtn: String;
  clickedYes: boolean = false;

  ngOnInit() {
    this.dialogHeader = this.data.dialogHeader;
    this.dialogQuestion = this.data.dialogQuestion;
    this.yesStrBtn = this.data.yesStrBtn;
    this.noStrBtn = this.data.noStrBtn;
  }
}
