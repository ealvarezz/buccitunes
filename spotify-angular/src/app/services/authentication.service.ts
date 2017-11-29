import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import {User} from '../objs/User';
import {BucciResponse} from '../objs/BucciResponse';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class AuthenticationService {
    constructor(private http: HttpClient) { }

    login(username : string, password: string){

        return this.http.post<BucciResponse<User>>('http://localhost:8080/login', { email: username, password: password }, {withCredentials: true})
            .map(bucci => {
                if(bucci){
                    if(bucci.successful){
                        localStorage.setItem('currentUser', JSON.stringify(bucci.response))
                        return bucci.response;
                    }
                    else{
                        throw new Error(bucci.message);
                    }
                }
                
            })
            .catch((error : any) =>{
                return Observable.throw(new Error(error));
            });
    }


    
    logout(){
        return this.http.post<BucciResponse<User>>('http://localhost:8080/logout', null, {withCredentials: true})
        .map(bucci =>{
            if(bucci.successful){
                localStorage.removeItem('currentUser');
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

    getLoggedInUser() : User{
        return JSON.parse(localStorage.getItem('currentUser'));
    }

}
