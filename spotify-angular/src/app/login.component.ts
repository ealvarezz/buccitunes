import { Component, OnInit} from '@angular/core';
import { FormControl, Validators } from "@angular/forms";
import { Observable } from 'rxjs/Observable';
import {AuthenticationService} from './services/authentication.service'
import { Router, ActivatedRoute } from "@angular/router"; 
import 'rxjs/add/operator/catch';

const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

@Component({
  selector: "login-component",
  templateUrl: "../views/login.component.html",
  styleUrls: ["../views/styles/login.component.css"]
})
export class LoginComponent implements OnInit{
  private userName : string  = "";
  private password : string = "";
  private returnUrl : string;

constructor(private route: ActivatedRoute, private router: Router, private authenticationService: AuthenticationService) {}

  ngOnInit(){
    this.authenticationService.logout();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }


  emailFormControl = new FormControl("", [
    Validators.required,
    Validators.email
  ]);

passwordFormControl = new FormControl("", [
    Validators.required
  ]);

getErrorMessage() {
    return this.emailFormControl.hasError('required') ? 'You must enter a value' :
        this.emailFormControl.hasError('email') ? 'Not a valid email' :
        this.emailFormControl.hasError('credentials')  ? 'Invalid email or password':
            '';
  }

getPasswordMessage(){
  return this.passwordFormControl.hasError('credentials') ? 'Invalid email or password':
    '';
}

  loginUser() {
    //Do Stuff to verify user
    this.authenticationService.login(this.userName, this.password)
            .subscribe(
                (data) => {
                    this.router.navigate([this.returnUrl]);
                },
                (err) => {
                    this.emailFormControl.setErrors({'credentials': true})
                    this.passwordFormControl.setErrors({'credentials': true})
                });
    //this.router.navigate([""]);
  }

  goSignUp() {
    this.router.navigate(["sign-up"]);
  }
}

