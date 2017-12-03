import { Injectable } from '@angular/core';
import {BucciResponse} from '../objs/BucciResponse';

import {environment} from '../../environments/environment';

import {Album} from '../objs/Album';
import {RequestedAlbum} from '../objs/RequestedAlbum';
import {Playlist} from '../objs/Playlist';
import {User} from '../objs/User';
import {Song} from '../objs/Song';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class MusicCollectionService {

    songLibrary : BehaviorSubject<Song[]> = new BehaviorSubject([]);

    constructor(private http: HttpClient){}

    getAlbum(id: number){
        return this.http.get<BucciResponse<Album>>(environment.GET_ALBUM_ENDPOINT,{
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

    addAlbumAdmin(album : RequestedAlbum){
        console.log(album);
        return this.http.post<BucciResponse<Album>>(environment.ADD_ALBUM_ENDPOINT_ADMIN,
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

    addAlbumArtist(album: RequestedAlbum){
        console.log(album);
        return this.http.post<BucciResponse<Album>>(environment.ADD_ALBUM_ENDPOINT_ARTIST,
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
        return this.http.post<BucciResponse<Playlist>>(environment.ADD_PLAYLIST_ENDPOINT,
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

    addSongToLibrary(song : Song) {
        return this.http.post<BucciResponse<String>>(environment.SAVE_SONG_ENDPOINT, song, {withCredentials: true}).
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

    getSongLibrary(){
        return this.http.get<BucciResponse<Song[]>>(environment.GET_SONG_LIBRARY_ENDPOINT,{withCredentials: true}).
        map(bucci =>{
            if(bucci.successful){
                this.songLibrary.next(bucci.response);
                return bucci.response;
            }
            else{
                throw new Error(bucci.message);
            }
        }).catch((error : any) =>{
            return Observable.throw(new Error(error.message));
        });
    }

    saveAlbum(album : Album){
        return this.http.post<BucciResponse<String>>(environment.SAVE_ALBUM_ENDPOINT, album, {withCredentials: true}).
        map(bucci =>{
            if (bucci.successful){
                return bucci.response;
            } 
            else{
                throw new Error(bucci.message);
            }
        })
        .catch((error: any) =>{
            return Observable.throw(new Error(error.message));
        }
        );
    }

    getAlbumLibrary(){
        return this.http.get<BucciResponse<Album[]>>(environment.GET_SAVED_ALBUMS_ENDPOINT, {withCredentials: true})
        .map(bucci =>{
            if (bucci.successful){
                return bucci.response;
            } 
            else{
                throw new Error(bucci.message);
            }
        })
        .catch((error: any) =>{
            return Observable.throw(new Error(error.message));
        }
        );
    }

    getRecentlyPlayedAlbums(){
        return this.http.get<BucciResponse<Album[]>>(environment.GET_SAVED_ALBUMS_ENDPOINT, {withCredentials: true})
        .map(bucci =>{
            if (bucci.successful){
                return bucci.response;
            } 
            else{
                throw new Error(bucci.message);
            }
        })
        .catch((error: any) =>{
            return Observable.throw(new Error(error.message));
        }
        );
    }

    private handleSuccessResponse(bucci : BucciResponse<any>){
        if (bucci.successful){
            return bucci.response;
        }
        else{
            throw new Error(bucci.message);
        }
    }

    private handleError(operation: string) {
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
