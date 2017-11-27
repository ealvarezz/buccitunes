import { Injectable } from '@angular/core';
import {BucciResponse} from '../objs/BucciResponse';

import {Album} from '../objs/Album';
import {RequestedAlbum} from '../objs/RequestedAlbum';
import {Playlist} from '../objs/Playlist';
import {User} from '../objs/User';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class MusicCollectionService {

    constructor(private http: HttpClient){}

    getAlbum(id: number){
        return this.http.get<BucciResponse<Album>>('http://localhost:8080/album',{
           params: new HttpParams().set('id', String(id)), 
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

    addAlbum(album : RequestedAlbum){
        console.log(album);
        return this.http.post<BucciResponse<Album>>('http://localhost:8080/approveAlbum',
         album, 
         { withCredentials: true }).
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

    addPlaylist(playlist : Playlist){
        return this.http.post<BucciResponse<Playlist>>('http://localhost:8080/newplaylist',
         playlist,
        { withCredentials: true }).
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

}
