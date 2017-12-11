import { Component, Inject } from "@angular/core";
import { MdDialog, MdDialogRef, MD_DIALOG_DATA } from "@angular/material";
import { FormControl, Validators } from "@angular/forms";

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
  clickedYes: boolean = true;
  putInText: boolean = false;
  textInput: string;
  textFormControl = new FormControl("", [Validators.required]);
  inputType : string;

  ngOnInit() {
    this.dialogHeader = this.data.dialogHeader;
    this.dialogQuestion = this.data.dialogQuestion;
    this.yesStrBtn = this.data.yesStrBtn;
    this.noStrBtn = this.data.noStrBtn;
    this.putInText = this.data.putInText;
  }
}
