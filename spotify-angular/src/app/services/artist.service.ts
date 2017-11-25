import { Injectable } from '@angular/core';
import {Artist } from '../objs/Artist';
import {BucciResponse} from '../objs/BucciResponse';

// import {Http, Response, RequestOptions, Headers, URLSearchParams} from '@angular/http';
import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class ArtistService {
    constructor(private http: HttpClient) { }

    getArtist(id : number) : Observable<Artist> {


        return this.http.get<BucciResponse<Artist>>('http://localhost:8080/artist',{
           params: new HttpParams().set('id', String(id))
        }).
        map(bucci  => {
            console.log(bucci);
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
        




    private handleError(operation: String) {
        return (err: any) => {
            let errMsg = `error in ${operation}() retrieving /artist`;
            console.log(`${errMsg}:`, err)
            if(err instanceof HttpErrorResponse) {
                // you could extract more info about the error if you want, e.g.:
                console.log(`status: ${err.status}, ${err.statusText}`);
                // errMsg = ...
            }
            return Observable.throw(errMsg);
        }
    }
}
