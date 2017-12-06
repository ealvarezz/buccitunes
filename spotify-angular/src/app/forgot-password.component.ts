import { Component, OnInit} from '@angular/core';
import { FormControl, Validators } from "@angular/forms";
import { Observable } from 'rxjs/Observable';
import {AuthenticationService} from './services/authentication.service'
import { Router, ActivatedRoute } from "@angular/router"; 
import {NotificationsService} from 'angular4-notifications';
import 'rxjs/add/operator/catch';

const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

@Component({
  selector: "forgot-component",
  templateUrl: "../views/forgot-password.component.html",
  styleUrls: ["../views/styles/login.component.css"]
})
export class ForgotComponent implements OnInit{
  private email : string;
  confirmed : boolean = false;

constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(){
  }


  emailFormControl = new FormControl("", [
    Validators.required,
    Validators.email
  ]);

getErrorMessage() {
    return this.emailFormControl.hasError('required') ? 'You must enter a value' :
        this.emailFormControl.hasError('email') ? 'Not a valid email' :
        this.emailFormControl.hasError('credentials')  ? 'Invalid email or password':
            '';
  }


resetPassword(){
    this.authenticationService.resetPasswordRequest(this.email).subscribe(
        (data)=>{
            this.confirmed = true;
        },
        (err)=>{
            this.confirmed = false;
            this.emailFormControl.setErrors({'credentials': true})
        }
    )
}
}

@Component({
  selector: "recover-component",
  templateUrl: "../views/recover.component.html",
  styleUrls: ["../views/styles/login.component.css"]
})
export class RecoverComponent implements OnInit{
  private email : string;
  private hash : string;
  private password : string;
  private confirmPassword: string;
  confirmed : boolean = false;

constructor(private route: ActivatedRoute,
            private router: Router,
            private authenticationService: AuthenticationService,
            private notifications : NotificationsService) {}

  ngOnInit(){
    this.route.params.subscribe(params => {
        this.email = params['email'];
        this.hash = params['hash'];
        this.validateURL();
    });
  }

  validateURL(){
    this.authenticationService.getResetPassword(this.email, this.hash).subscribe(
      (data)=>{
        
      },
      (err)=>{
        this.router.navigate(["/login"]);
      }
    )
  }


  passwordFormControl = new FormControl("", [
    Validators.required,
  ]);

    confirmPasswordFormControl = new FormControl("", [
    Validators.required,
  ]);

getErrorMessage(form : FormControl) {
    return form.hasError('required') ? 'You must enter a value' : '';
  }


  changePassword(){
    this.authenticationService.changePassword(this.email, this.password).subscribe(
      (data)=>{
        this.confirmed = true;
      },
      (err) =>{
        this.confirmed = false;
        this.notifications.error("ERROR",err);
      }
    )
  }

// resetPassword(){
//     this.authenticationService.resetPasswordRequest(this.email).subscribe(
//         (data)=>{
//             this.confirmed = true;
//         },
//         (err)=>{
//             this.confirmed = false;
//             this.emailFormControl.setErrors({'credentials': true})
//         }
//     )
// }
}


