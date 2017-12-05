import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {User} from '../objs/User';
import {UserPage} from '../objs/UserPage';
import {BucciResponse} from '../objs/BucciResponse';
import {environment} from '../../environments/environment';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    getUser(userId : string){


        return this.http.post<BucciResponse<UserPage>>(environment.GET_USER_ENDPOINT,JSON.stringify(userId),{
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
           withCredentials: true
        }).
        map(bucci  => {
                if(bucci.successful){
                    return bucci.response;
                }
                else{
                    throw new Error(bucci.message);
                }
                
            }).catch((error : any) =>{
                return Observable.throw(new Error(error.message));
            });
    }

    followUser(user : User){
        let sanitizedUser = this.sanitzeUser(user);
        return this.http.post<BucciResponse<String>>(environment.FOLLOW_USER_ENDPOINT, sanitizedUser, {withCredentials: true}).
         map(bucci  => {
                if(bucci.successful){
                    return bucci.response;
                }
                else{
                    throw new Error(bucci.message);
                }
                
            }).catch((error : any) =>{
                return Observable.throw(new Error(error.message));
            });
    }

    unfollowUser(user : User){
        let sanitizedUser = this.sanitzeUser(user);
        return this.http.post<BucciResponse<String>>(environment.UNFOLLOW_USER_ENDPOINT, sanitizedUser, {withCredentials: true}).
         map(bucci  => {
                if(bucci.successful){
                    return bucci.response;
                }
                else{
                    throw new Error(bucci.message);
                }
                
            }).catch((error : any) =>{
                return Observable.throw(new Error(error.message));
            });
    }

    private sanitzeUser(user : User){
        let newUser = new User();
        newUser.email = user.email;
        return newUser;
    }
    

}