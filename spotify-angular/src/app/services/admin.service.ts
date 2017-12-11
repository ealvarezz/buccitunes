import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {environment} from '../../environments/environment';
import {BucciConstants} from '../../environments/app.config';
import {User} from '../objs/User';
import {RequestedAlbum} from '../objs/RequestedAlbum';
import {BucciResponse} from '../objs/BucciResponse';
import {Concert} from '../objs/Concert';

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
        
        return this.http.get<BucciResponse<RequestedAlbum[]>>(BucciConstants.Endpoints.GET_REQ_ALBUMS,{ withCredentials: true })
        .map(this.extractData)
        .catch(this.handleError);
    }

    getRequestedAlbum(album : RequestedAlbum) {
        return this.http.get<BucciResponse<RequestedAlbum>>(BucciConstants.Endpoints.GET_REQ_ALBUM, { 
            params: new HttpParams().set('id', String(album.id)),  withCredentials: true })
        .map(this.extractData)
        .catch(this.handleError)
    }
    //change endpoint
    getRequestedSongs(){
        return this.http.get<BucciResponse<RequestedAlbum>>(environment.GET_REQ_ALBUM_ENDPOINT, {withCredentials: true})   
        .map(this.extractData)
        .catch(this.handleError)
    }

    approveAlbum(album : RequestedAlbum){
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.APPROVE_ALBUM, album, {withCredentials: true})
        .map(this.extractData)
        .catch(this.handleError)
        
    }

    rejectAlbum(album: RequestedAlbum){
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.REJECT_ALBUM, album, {withCredentials: true})
        .map(this.extractData)
        .catch(this.handleError)
    }
    
    getRequestedConcerts(){
        return this.http.get<BucciResponse<Concert[]>>(BucciConstants.Endpoints.GET_REQUESTED_CONCERTS, {withCredentials: true})   
        .map(this.extractData)
        .catch(this.handleError)
    }

    approveRequestedConcert(concert : Concert){
        return this.http.post<BucciResponse<Concert>>(BucciConstants.Endpoints.APPROVE_CONCERT, concert, {withCredentials: true})   
        .map(this.extractData)
        .catch(this.handleError)
    }

    rejectRequestedConcert(concert : Concert){
        return this.http.post<BucciResponse<Concert>>(BucciConstants.Endpoints.REJECT_CONCERT, concert, {withCredentials: true})   
        .map(this.extractData)
        .catch(this.handleError)
    }

    private extractData(bucci){
        if(bucci.successful){
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