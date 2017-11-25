import { Injectable } from '@angular/core';
import {BucciResponse} from '../objs/BucciResponse';

import {Album} from '../objs/Album';
import {Playlist} from '../objs/Playlist';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class MusicCollectionService {

    constructor(private http: HttpClient){}
   
    getAlbum(id: number){
        return this.http.get<BucciResponse<Album>>('http://localhost:8080/album',{
           params: new HttpParams().set('id', String(id))
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

    addAlbum(album : Album){
        console.log(album);
        return this.http.post<BucciResponse<Album>>('http://localhost:8080/approveAlbum', album).
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
        return this.http.post<BucciResponse<Playlist>>('http://localhost:8080/newPlaylist',playlist).
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
