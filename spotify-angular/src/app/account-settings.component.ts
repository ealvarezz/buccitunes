import { Component } from '@angular/core';
import { User } from './objs/User';
import {Payment} from './objs/Payment';


@Component({
  selector: 'account-settings',
  templateUrl: '../views/account-settings.component.html',
  styleUrls:["../views/styles/account-settings.css"]
})
export class AccountSettingsComponent {
  editMode : boolean = false;
  receipts : Payment[] = [new Payment(), new Payment(), new Payment(), new Payment()]

  toggleEditMode(){
    this.editMode = !this.editMode;
  }
}