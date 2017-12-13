import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {SignupInfo} from '../objs/SignupInfo';
import {User} from '../objs/User';
import {BucciResponse} from '../objs/BucciResponse';
import {BucciConstants} from '../../environments/app.config';
import {BillingInfo} from '../objs/BillingInfo';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/observable/of';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class AuthenticationService {

    currentUserChange: BehaviorSubject<User>

    constructor(private http: HttpClient) {
        this.currentUserChange = new BehaviorSubject(new User());
     }


    get currentUser() : User {return this.currentUserChange.value}


    signUp(user : SignupInfo){
        return this.http.post<BucciResponse<User>>(BucciConstants.Endpoints.SIGN_UP, user, {withCredentials: true})
        .map(this.extractData.bind(this))
        .catch(this.handleError);
    }

    upgradeToPremium(billingInfo : BillingInfo){
        return this.http.post<BucciResponse<User>>(BucciConstants.Endpoints.UPGRADE_TO_PREMIUM, billingInfo, {withCredentials: true})
        .map(this.extractDataBUCCI)
        .catch(this.handleError);
    }

    login(username : string, password: string){
        return this.http.post<BucciResponse<User>>(BucciConstants.Endpoints.LOGIN, { email: username, password: password }, {withCredentials: true})
            .map(this.extractData.bind(this))
            .catch(this.handleError);
    }


    
    logout(){
        
        return this.http.post<BucciResponse<User>>(BucciConstants.Endpoints.LOGOUT, null, {withCredentials: true})
        .map(bucci =>{
            if(bucci.successful){
                return bucci.response;
            }
            else{
                throw new Error(bucci.message);
            }
        })
        .catch((error : any) =>{
            return Observable.throw(new Error(error));
        });
        
    }

    togglePrivate(secret : boolean){
        return this.http.put(BucciConstants.Endpoints.GO_SECRET_MODE, null, {
            params: new HttpParams().set('secret', String(secret)), 
            withCredentials: true
        })
        .map(this.extractData.bind(this))
        .catch(this.handleError);
    }

    resetPasswordRequest(email : String){
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.RESET_PW_REQUEST, JSON.stringify(email), 
        {headers: new HttpHeaders().set('Content-Type', 'application/json'), 
        withCredentials: true})
        .map(this.extractDataBUCCI)
        .catch(this.handleError);
    }

    getResetPassword(email : String, hashString : String){
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.GET_RESET_PW, {email: email, password: hashString}, {withCredentials: true})
        .map(this.extractDataBUCCI)
        .catch(this.handleError);
    }
    changePassword(email : String, password : String){
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.RESET_PASSWORD, {email: email, password: password}, {withCredentials: true})
        .map(this.extractDataBUCCI)
        .catch(this.handleError);
    }

    getLoggedInUser() : Observable<User>{
        return this.http.get<BucciResponse<User>>(BucciConstants.Endpoints.LOGGED_IN_USER,{withCredentials: true})
        .map(this.extractData.bind(this))
        .catch(this.handleError);
    }
    cancelSubscription(){
        return this.http.put<BucciResponse<User>>(BucciConstants.Endpoints.CANCEL_PREMIUM,null,{withCredentials : true})
        .map(this.extractDataBUCCI)
        .catch(this.handleError);
    }

    makePayment(){
        return this.http.put<BucciResponse<User>>(BucciConstants.Endpoints.MAKE_PAYMENT,null,{withCredentials : true})
        .map(this.extractDataBUCCI)
        .catch(this.handleError);
    }


    isAdminUserRole() : boolean{
        return this.currentUser.role === BucciConstants.Roles.ADMIN;
    }

    isArtistUserRole() : boolean{
        return this.currentUser.role === BucciConstants.Roles.ARTIST;
    }

    isPremiumUserRole() : boolean{
        return this.currentUser.role === BucciConstants.Roles.PREMIUM;
    }

    private extractDataBUCCI(bucci){
        if(bucci.successful){
            return bucci.response;
        }
        else{
            throw new Error(bucci.message);
        }
    }

    private extractData(bucci){
        if(bucci.successful){
            if(this.currentUserChange){
                this.currentUserChange.next(bucci.response);
            }
            return bucci.response;
        }
        else{
            throw new Error(bucci.message);
        }
    }

    private handleError(error: any) {
        return Observable.throw(new Error(error.message));
    }

}
