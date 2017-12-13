import { Component, OnInit, ViewChild } from '@angular/core';
import {User} from './objs/User';
import {BillingInfo, CreditCardCompany, CREDIT_CARD_ENUM} from './objs/BillingInfo';
import {SignupInfo} from './objs/SignupInfo';
import {AuthenticationService} from './services/authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router } from '@angular/router';

@Component({
  selector: 'cancel-premimum',
  templateUrl: '../views/cancel-subscription.html',
  styleUrls: ['../views/styles/sign-up.component.css']
})
export class CancelSubscriptionComponent implements OnInit {

thirdFormGroup: FormGroup;

notCompleted = true;
@ViewChild('stepperz') stepper;


constructor(private _formBuilder: FormBuilder, private authentication : AuthenticationService, private router : Router) {

   }

ngOnInit() {
    this.thirdFormGroup = this._formBuilder.group({
    });

  }


  cancel(){
    this.authentication.cancelSubscription().subscribe(
      (data) =>{
        this.authentication.logout().subscribe(
            (data)=>{
                console.log("logged out");
                this.stepper.selectedIndex = 1;
            }
        )
      },
      (err) =>{
        console.error(err);
      }
    )
  
  
  }

}