import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {BucciConstants} from '../../environments/app.config';
import {User} from '../objs/User';
import {Artist} from '../objs/Artist';
import { Payment } from "../objs/Payment";
import {UserPage} from '../objs/UserPage';
import {BucciResponse} from '../objs/BucciResponse';
import {environment} from '../../environments/environment';
import {Activity} from '../objs/Activity';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import { BillingInfo } from '../objs/BillingInfo';


@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  getUser(userId: string) {
    return this.http
      .post<BucciResponse<UserPage>>(
        BucciConstants.Endpoints.GET_USER,
        JSON.stringify(userId),
        {
          headers: new HttpHeaders().set("Content-Type", "application/json"),
          withCredentials: true
        }
      )
      .map(this.extractData)
      .catch(this.handleError);
  }

  getFollowedArtists(){
    return this.http.get<BucciResponse<User[]>>(BucciConstants.Endpoints.GET_FOLLOWED_ARTIST, {
        withCredentials: true
      })
      .map(this.extractData)
      .catch(this.handleError);
  }

  getAllUser() {
    return this.http
      .get<BucciResponse<User[]>>(BucciConstants.Endpoints.GET_ALL_USERS, {
        withCredentials: true
      })
      .map(this.extractData)
      .catch(this.handleError);
  }

  followUser(user: User) {
    let sanitizedUser = this.sanitzeUser(user);
    return this.http
      .post<BucciResponse<String>>(
        BucciConstants.Endpoints.FOLLOW_USER,
        sanitizedUser,
        { withCredentials: true }
      )
      .map(this.extractData)
      .catch(this.handleError);
  }

  unfollowUser(user: User) {
    let sanitizedUser = this.sanitzeUser(user);
    return this.http
      .post<BucciResponse<String>>(
        BucciConstants.Endpoints.UNFOLLOW_USER,
        sanitizedUser,
        { withCredentials: true }
      )
      .map(this.extractData)
      .catch(this.handleError);
  }

  changeUserInfo(user: User) {
    return this.http
      .post<BucciResponse<User>>(
        BucciConstants.Endpoints.CHANGE_USER_INFO,
        user,
        { withCredentials: true }
      )
      .map(this.extractData)
      .catch(this.handleError);
  }

  getPaymentInfo() {
    return this.http
      .get<BucciResponse<Payment>>(
        BucciConstants.Endpoints.GET_PAYMENT_HISTORY,
        { withCredentials: true }
      )
      .map(this.extractData)
      .catch(this.handleError);
  }

  cancelPremiumAccount() {
    return this.http
      .put<BucciResponse<User>>(BucciConstants.Endpoints.CANCEL_PREMIUM, {
        withCredentials: true
      })
      .map(this.extractFullData)
      .catch(this.handleError);
  }

  changePasswordSetting(passwordOld: string, passwordNew: string) {
    return this.http
      .post<BucciResponse<User>>(BucciConstants.Endpoints.RESET_PASS_NO_MAIL, { current: passwordOld, toNew: passwordNew }, { withCredentials: true })
      .map(this.extractData)
      .catch(this.handleError);
  }

  changeBillingInfo(billingInfo: BillingInfo) {
    return this.http
      .post<BucciResponse<User>>(BucciConstants.Endpoints.CHANGE_BILLING_INFO, billingInfo, { withCredentials: true })
      .map(this.extractData)
      .catch(this.handleError);
  }
  
  getActivityFeed(){
    return this.http
      .get<BucciResponse<Activity[]>>(
        BucciConstants.Endpoints.GET_ACTIVITY_FEED,
        { withCredentials: true }
      )
      .map(this.extractData)
      .catch(this.handleError);
  }

  private sanitzeUser(user: User) {
    let newUser = new User();
    newUser.email = user.email;
    return newUser;
  }

  private extractData(bucci) {
    if (bucci.successful) {
      return bucci.response;
    } else {
      throw new Error(bucci.message);
    }
  }

  private extractFullData(bucci) {
    if (bucci.successful) {
      return bucci;
    } else {
      throw new Error(bucci.message);
    }
  }

  private handleError(error: any) {
    return Observable.throw(new Error(error.message));
  }
}