import { Component, OnInit } from '@angular/core';
import { User } from './objs/User';
import {Payment} from './objs/Payment';
import {CREDIT_CARD_ENUM} from './objs/BillingInfo';
import {AuthenticationService } from './services/authentication.service';
import { MediaService } from "./services/media.service";
import { UserService } from "./services/user.service";
import { MediaFile } from "./objs/MediaFile";
import { environment } from "../environments/environment";
import { NotificationsService } from "angular4-notifications";

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
  ) {}

  user: User;
  step: number;
  editMode: boolean = false;
  receipts: Payment[];
  renewDate: Date;
  previewCC: string;

  nameForUser: string;
  usernameForUser: string;
  avatarArtwork: string;
  

  ngOnInit() {
    this.authentication.currentUserChange.subscribe(user => {
      this.user = user;
      this.renewDate = new Date(2018, 0, 7);

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

        this.userService.getPaymentInfo().subscribe(
          data => {
            this.receipts = data;
          }
        );
      }
      this.nameForUser = user.name;
      this.usernameForUser = user.username;
    });
  }

  submitUserChange() {
    var changedUser = new User();
    //changedUser.email = this.user.email;
    changedUser.name = this.nameForUser;
    changedUser.username = this.usernameForUser;
    if (this.avatarArtwork) changedUser.avatar = this.avatarArtwork.split(",")[1];
    this.userService.changeUser(changedUser).subscribe(
      data => {
        this.user = data;
        this.notificationService.success("SUCCESS", "The change has been made");
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

  uploadImage(event) {
    this.mediaService.previewImage(event).subscribe((data: MediaFile) => {
      this.avatarArtwork = data.artwork;
    });
  }
}