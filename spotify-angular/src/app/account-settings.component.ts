import { Component, OnInit } from '@angular/core';
import { User } from './objs/User';
import {Payment} from './objs/Payment';
import {CREDIT_CARD_ENUM} from './objs/BillingInfo';
import {AuthenticationService } from './services/authentication.service';


@Component({
  selector: 'account-settings',
  templateUrl: '../views/account-settings.component.html',
  styleUrls:["../views/styles/account-settings.css"]
})
export class AccountSettingsComponent implements OnInit {

  constructor(private authentication : AuthenticationService){}

  user : User;
  step : number;
  editMode : boolean = false;
  receipts : Payment[] = [new Payment(), new Payment(), new Payment(), new Payment()]
  renewDate : Date;
  previewCC : string;
  

  ngOnInit(){
    this.authentication.currentUserChange.subscribe(
      (user) => {
        this.user = user
        this.renewDate = new Date(2018,0,7);


        // if(this.user.join_date.getDay() < new Date().getDay()){

        //   this.renewDate.setMonth(new Date().getMonth() + 1);
        //   this.renewDate.setDate(this.user.join_date.getDay());
        // }
        // else{
        //   this.renewDate.setDate(this.user.join_date.getDay());
        // }

        if(user.billingInfo){
          this.previewCC = this.user.billingInfo.creditCardNo.slice(-4);
          this.user.billingInfo.creditCardCompany = this.getCreditCardCompany();
        }
      }
    );

    

  }

  getCreditCardCompany(){
    let cc_id = this.user.billingInfo.creditCardCompany.id
    for(let company of CREDIT_CARD_ENUM){
      if(cc_id == company.id ){
        return company;
      }
    }
  }
  setStep(step : number){
    this.step = step;
  }
  toggleEditMode(){
    this.editMode = !this.editMode;
  }


}