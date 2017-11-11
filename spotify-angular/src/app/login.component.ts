import { Component} from '@angular/core';
import { FormControl, Validators } from "@angular/forms";
import { Router } from "@angular/router"; 

const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

@Component({
  selector: "login-component",
  templateUrl: "../views/login.component.html",
  styleUrls: ["../views/styles/login.component.css"]
})
export class LoginComponent {
  private userName = "";
  private password = "";

  emailFormControl = new FormControl("", [
    Validators.required,
    Validators.pattern(EMAIL_REGEX)
  ]);

  constructor(private router: Router) {}

  loginUser() {
    //Do Stuff to verify user

    this.router.navigate([""]);
  }

  goSignUp() {
    this.router.navigate(["sign-up"]);
  }
}
