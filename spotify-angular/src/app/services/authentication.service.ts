import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {Http, Response, RequestOptions, Headers} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {BucciResponse} from '../objs/BucciResponse';

@Injectable()
export class AuthenticationService {
    constructor(private http: Http) { }

    login(username : string, password: string){
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post('http://localhost:8080/login', { email: username, password: password })
            .map((response: Response) => {
                if(response){
                    let bucci = response.json();
                    if(bucci['successful'] == true){
                        localStorage.setItem('currentUser',bucci['response'])
                        return bucci['response'];
                    }
                    else{
                        throw new Error(bucci['message']);
                    }
                }
                
            }).catch((error : any) =>{
                return Observable.throw(new Error(error));
            });
    }
    
    logout(){
        localStorage.removeItem('currentUser');
    }

    getLoggedInUser(){
        return localStorage.getItem('currentUser');
    }
}
