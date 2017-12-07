import { Component, OnInit } from '@angular/core';
import {User} from './objs/User';
import {BillingInfo, CreditCardCompany, CREDIT_CARD_ENUM} from './objs/BillingInfo';
import {SignupInfo} from './objs/SignupInfo';
import {AuthenticationService} from './services/authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'sign-up',
  templateUrl: '../views/sign-up.component.html',
  styleUrls: ['../views/styles/sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  isLinear = false;
  type : string;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  selectedMonth : string;

  newUser : User;
  firstName: string;
  lastName : string;
  billingInfo : BillingInfo;

  companies : CreditCardCompany[];
  creditCardCompany : CreditCardCompany;

  months : string[] = ['January','Feburary','March','April','May','June','July','August','September','October','November','Decemeber']

  constructor(private _formBuilder: FormBuilder, private authentication : AuthenticationService) {
    this.newUser = new User();
    this.billingInfo = new BillingInfo();
    this.companies = CREDIT_CARD_ENUM.slice();

   }

  ngOnInit() {

    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
        this.thirdFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });

  }

  setUserType(type){
    this.type = type;
  }

  signUp(){

    this.newUser.name = this.firstName + " " +this.lastName;

    let signUpInfo = new SignupInfo();
    signUpInfo.userInfo = this.newUser;

    if(this.type === 'premium'){
      signUpInfo.billingInfo = this.billingInfo;
    }
    
    this.authentication.signUp(signUpInfo).subscribe(
      (data) =>{
        console.log(data);
      },
      (err) =>{
        console.error(err);
      }
    )
  
  
  }



}
