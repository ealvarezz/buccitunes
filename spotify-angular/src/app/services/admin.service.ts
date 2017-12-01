import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {environment} from '../../environments/environment';

import {User} from '../objs/User';
import {RequestedAlbum} from '../objs/RequestedAlbum';
import {BucciResponse} from '../objs/BucciResponse';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class AdminService {
    constructor(private http: HttpClient) { }

    getRequestedAlbums() {
        return this.http.get<BucciResponse<RequestedAlbum[]>>(environment.GET_REQ_ALBUMS_ENDPOINT,{ withCredentials: true })
        .map(bucci  => {
            console.log(bucci);
                if(bucci.successful){
                    return bucci.response;
                }
                else{
                    throw new Error(bucci.message);
                } 
            })
            .catch((error : any) =>{
                return Observable.throw(new Error(error.message));
            });
    }

    getRequestedAlbum(album : RequestedAlbum) {
        return this.http.get<BucciResponse<RequestedAlbum>>(environment.GET_REQ_ALBUMS_ENDPOINT, { withCredentials: true })
        .map(bucci =>{
            if(bucci.successful){
                return bucci.response;
            }
            else{
                throw new Error(bucci.message);
            }
        })
        .catch((error : any)=>{
            return Observable.throw(new Error(error.message));
        })
    }

}