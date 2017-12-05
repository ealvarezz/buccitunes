import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import {User} from '../objs/User';
import {BucciResponse} from '../objs/BucciResponse';
import {BucciConstants} from '../../environments/app.config';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class AuthenticationService {

    currentUserChange: BehaviorSubject<User>

    constructor(private http: HttpClient) {
        this.currentUserChange = new BehaviorSubject(new User());
     }


    get currentUser() : User {return this.currentUserChange.value}

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

    getLoggedInUser() : Observable<User>{
        return this.http.get<BucciResponse<User>>(BucciConstants.Endpoints.LOGGED_IN_USER,{withCredentials: true})
        .map(this.extractData.bind(this))
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


    private extractData(bucci){
        if(bucci.successful){
            this.currentUserChange.next(bucci.response);
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
