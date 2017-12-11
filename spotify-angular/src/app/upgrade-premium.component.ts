import { Component, OnInit, ViewChild } from '@angular/core';
import {User} from './objs/User';
import {BillingInfo, CreditCardCompany, CREDIT_CARD_ENUM} from './objs/BillingInfo';
import {SignupInfo} from './objs/SignupInfo';
import {AuthenticationService} from './services/authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';


@Component({
  selector: 'sign-up',
  templateUrl: '../views/upgrade-premium.html',
  styleUrls: ['../views/styles/sign-up.component.css']
})
export class UpgradePremiumComponent implements OnInit {

billingInfo : BillingInfo;
thirdFormGroup: FormGroup;
companies : CreditCardCompany[];
creditCardCompany : CreditCardCompany;
notCompleted = true;
@ViewChild('stepper') stepper;
months : string[] = ['January','Feburary','March','April','May','June','July','August','September','October','November','Decemeber']


constructor(private _formBuilder: FormBuilder, private authentication : AuthenticationService) {
    this.billingInfo = new BillingInfo();
    this.companies = CREDIT_CARD_ENUM.slice();
   }

ngOnInit() {
    this.thirdFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });

  }


  upgrade(){
    
    this.authentication.upgradeToPremium(this.billingInfo).subscribe(
      (data) =>{
        this.stepper.selectedIndex = 1;
      },
      (err) =>{
        console.error(err);
      }
    )
  
  
  }

}