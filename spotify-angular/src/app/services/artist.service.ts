import { Injectable, Inject} from '@angular/core';
import {Artist } from '../objs/Artist';
import {Concert} from '../objs/Concert';
import {User} from '../objs/User';
import {BucciResponse} from '../objs/BucciResponse';
import { environment } from '../../environments/environment';
import {BucciConstants} from '../../environments/app.config';
import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class ArtistService {

    constructor(private http: HttpClient) { }

    getArtist(id : number) : Observable<Artist> {
        
        return this.http.get<BucciResponse<Artist>>(BucciConstants.Endpoints.GET_ARTIST,{
           params: new HttpParams().set('id', String(id)), withCredentials: true
        }).
        map(this.extractData)
        .catch(this.handleError);
    }

    followArtist(artist : Artist){
        return this.http.get<BucciResponse<Artist[]>>(BucciConstants.Endpoints.FOLLOW_ARTIST, {
            params: new HttpParams().set('artistId', String(artist.id)), 
            withCredentials: true
        })
        .map(this.extractData)
        .catch(this.handleError)
    }

    unFollowArtist(artist : Artist){
        return this.http.get<BucciResponse<Artist[]>>(BucciConstants.Endpoints.UNFOLLOW_ARTIST, {
            params: new HttpParams().set('artistId', String(artist.id)), 
            withCredentials: true
        })
        .map(this.extractData)
        .catch(this.handleError)
    }

    updateArtist(artist : Artist){
        return this.http.post<BucciResponse<User>>(BucciConstants.Endpoints.UPDATE_ARTIST, artist, { withCredentials: true})
        .map(this.extractData)
        .catch(this.handleError)
    }
    
    getConcerts(artist : Artist){
        return this.http.post<BucciResponse<Concert[]>>(BucciConstants.Endpoints.GET_CONCERTS, artist, { withCredentials: true})
        .map(this.extractData)
        .catch(this.handleError)
    }

    getRelatedArtists(artist : Artist){
        return this.http.get<BucciResponse<Artist[]>>(BucciConstants.Endpoints.GET_RELATED_ARTISTS, {
            params: new HttpParams().set('artistId', String(artist.id)), 
            withCredentials: true
        })
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
