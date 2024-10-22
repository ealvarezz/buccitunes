import { Component, OnInit } from '@angular/core';
import { User } from './objs/User';
import {Payment} from './objs/Payment';
import {CREDIT_CARD_ENUM, BillingInfo, CreditCardCompany} from './objs/BillingInfo';
import {AuthenticationService } from './services/authentication.service';
import { MediaService } from "./services/media.service";
import { UserService } from "./services/user.service";
import { MediaFile } from "./objs/MediaFile";
import { YesNoDialogComponent } from "./yes-no-dialog.component";
import { environment } from "../environments/environment";
import { NotificationsService } from "angular4-notifications";
import { FormControl, Validators } from "@angular/forms";
import { MdDialog, MdDialogRef, MD_DIALOG_DATA } from "@angular/material";
import {Router } from '@angular/router';

@Component({
  selector: "account-settings",
  templateUrl: "../views/account-settings.component.html",
  styleUrls: ["../views/styles/account-settings.css"]
})
export class AccountSettingsComponent implements OnInit {
  constructor(
    private authentication: AuthenticationService,
    private notificationService: NotificationsService,
    public mediaService: MediaService,
    public userService: UserService,
    public dialog: MdDialog,
    private router : Router
  ) {}

  user: User;
  step: number;
  editMode: boolean = false;
  editModeBill: boolean = false;
  receipts: Payment[];
  renewDate: Date;
  previewCC: string;

  nameForUser: string;
  usernameForUser: string;
  avatarArtwork: string;

  currentPasswordFormControl = new FormControl("", [Validators.required]);
  newPasswordFormControl = new FormControl("", [Validators.required]);
  newPassRetypeFormControl = new FormControl("", [Validators.required]);
  currentPassword: string = "";
  newPassword: string = "";
  newPasswordRetype: string = "";

  billingInfoChange: BillingInfo;
  companies: CreditCardCompany[];

  ngOnInit() {
    this.authentication.currentUserChange.subscribe(user => {
      this.user = user;

      // if(this.user.join_date.getDay() < new Date().getDay()){

      //   this.renewDate.setMonth(new Date().getMonth() + 1);
      //   this.renewDate.setDate(this.user.join_date.getDay());
      // }
      // else{
      //   this.renewDate.setDate(this.user.join_date.getDay());
      // }

      if (user.billingInfo) {
        this.previewCC = this.user.billingInfo.creditCardNo.slice(-4);
        this.user.billingInfo.creditCardCompany = this.getCreditCardCompany();

        this.userService.getPaymentInfo().subscribe(data => {
          this.receipts = data;
          if(this.receipts.length > 0){
             this.renewDate = this.receipts[0].nextBillingDate;
          }
         
        });
        this.billingInfoChange = user.billingInfo;
      }
      this.nameForUser = user.name;
      this.usernameForUser = user.username;
      this.companies = CREDIT_CARD_ENUM.slice();
    });
  }

  submitUserChange() {
    var changedUser = new User();
    changedUser.email = this.user.email;
    changedUser.name = this.nameForUser;
    changedUser.username = this.usernameForUser;
    if (this.avatarArtwork)
      changedUser.avatar = this.avatarArtwork.split(",")[1];
    this.userService.changeUserInfo(changedUser).subscribe(
      data => {
        this.user = data;
        this.notificationService.success("SUCCESS", "The change has been made");
        this.editMode = false;
      },
      err => {
        this.notificationService.error("ERROR", err.message);
        console.log(err.message);
      }
    );
  }

  getCreditCardCompany() {
    let cc_id = this.user.billingInfo.creditCardCompany.id;
    for (let company of CREDIT_CARD_ENUM) {
      if (cc_id == company.id) {
        return company;
      }
    }
  }

  performStep() {}

  setStep(step: number) {
    this.step = step;
  }
  toggleEditMode() {
    this.editMode = !this.editMode;
  }
  toggleEditModeBill() {
    this.editModeBill = !this.editModeBill;
  }

  uploadImage(event) {
    this.mediaService.previewImage(event).subscribe((data: MediaFile) => {
      this.avatarArtwork = data.artwork;
    });
  }

  changePassword() {
    if (this.newPassword === this.newPasswordRetype) {
      this.userService
        .changePasswordSetting(
          this.currentPassword.trim(),
          this.newPassword.trim()
        )
        .subscribe(
          data => {
            this.user = data;
            this.notificationService.success(
              "SUCCESS",
              "Your password has changed"
            );
          },
          err => {
            this.currentPasswordFormControl.setErrors({ credentials: true });
          }
        );
    } else if (this.newPassword == "") {
      this.newPasswordFormControl.setErrors({ credentials: true });
    } else {
      this.newPassRetypeFormControl.setErrors({ credentials: true });
    }
  }

  cancelPremiumAccountConfirm() {
    var header = "Your Bucci Premium status will be lost";
    var message = "Are you sure to become Bucci Basic?";

    let dialogRef = this.dialog.open(YesNoDialogComponent, {
      width: "500px",
      data: { dialogHeader: header, dialogQuestion: message }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.router.navigate(['/cancel-subscription']);
    });
  }

  deleteAccountConfirm() {
    var header = "Are sure you want to delete your acccount and no longer be bucci?";
    var message = "Type in your password if you really want to be delete?";
    var passInput = true;

    let dialogRef = this.dialog.open(YesNoDialogComponent, {
      width: "600px",
      data: {
        dialogHeader: header,
        dialogQuestion: message,
        putInText: passInput
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if(result) {
        console.log("START DELETION!");
      }
    });
  }

  changePreviewCC() {
    if (this.user.billingInfo.creditCardNo.length >= 19) {
      this.previewCC = this.user.billingInfo.creditCardNo.slice(-4);
    }
  }

  changeBillingInformation() {
    this.userService.changeBillingInfo(this.billingInfoChange).subscribe(
      data => {
        this.user = data;
        this.notificationService.success(
          "SUCCESS",
          "Your billing information has changed"
        );
      },
      err => {
        this.currentPasswordFormControl.setErrors({ credentials: true });
      }
    );
  }

  cancelPremiumAccount() {
    this.userService.cancelPremiumAccount().subscribe(data => {
      this.user = data.response;
      this.notificationService.success("SUCCESS", data.message);
    });
  }
}