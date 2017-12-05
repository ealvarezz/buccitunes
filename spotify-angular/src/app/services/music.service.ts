import { Injectable, Inject } from '@angular/core';
import {BucciResponse} from '../objs/BucciResponse';

import {environment} from '../../environments/environment';
import {BucciConstants} from '../../environments/app.config';
import {IAppConfig} from '../../environments/app.config.interface';
import {Album} from '../objs/Album';
import {RequestedAlbum} from '../objs/RequestedAlbum';
import {Playlist} from '../objs/Playlist';
import {User} from '../objs/User';
import {Song} from '../objs/Song';
import {PlaylistPage} from '../objs/PlaylistPage';

import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class MusicCollectionService {

    songLibrary : BehaviorSubject<Song[]> = new BehaviorSubject([]);
    userPlaylists : BehaviorSubject<Playlist[]> = new BehaviorSubject([]);

    constructor(private http: HttpClient){}

    getAlbum(id: number){
        return this.http.get<BucciResponse<Album>>(BucciConstants.Endpoints.GET_ALBUM,{
           params: new HttpParams().set('id', String(id)), 
           withCredentials: true
        })
        .map(this.extractData)
        .catch(this.handleError);
    }

    getPlaylist(id : number){
        return this.http.get<BucciResponse<Album>>(BucciConstants.Endpoints.GET_PLAYLIST,{
           params: new HttpParams().set('id', String(id)), 
           withCredentials: true
        })
        .map(this.extractData)
        .catch(this.handleError);
    }

    addAlbumAdmin(album : RequestedAlbum){
        console.log(album);
        return this.http.post<BucciResponse<Album>>(BucciConstants.Endpoints.ADD_ALBUM_ADMIN,
        album, 
        { withCredentials: true })
        .map(this.extractData)
        .catch(this.handleError);

    }

    addAlbumArtist(album: RequestedAlbum){
        console.log(album);
        return this.http.post<BucciResponse<Album>>(BucciConstants.Endpoints.ADD_ALBUM_ARTIST,
         album, 
         { withCredentials: true }).
            map(this.extractData)
            .catch(this.handleError);

    }



    addPlaylist(playlist : Playlist){
        return this.http.post<BucciResponse<Playlist>>(BucciConstants.Endpoints.ADD_PLAYLIST,
         playlist,
        { withCredentials: true }).
        map(bucci  => {
                if(bucci.successful){
                    this.reloadPlaylists();
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
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.SAVE_SONG, song, {withCredentials: true}).
        map(this.extractData)
        .catch(this.handleError);
    }

    getSongLibrary(){
        return this.http.get<BucciResponse<Song[]>>(BucciConstants.Endpoints.GET_SONG_LIBRARY ,{withCredentials: true}).
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
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.SAVE_ALBUM, album, {withCredentials: true}).
        map(this.extractData)
        .catch(this.handleError);
    }

    getAlbumLibrary(){
        return this.http.get<BucciResponse<Album[]>>(BucciConstants.Endpoints.GET_SAVED_ALBUMS, {withCredentials: true})
        .map(this.extractData)
        .catch(this.handleError);
    }

    getRecentlyPlayedAlbums(){
        return this.http.get<BucciResponse<Album[]>>(BucciConstants.Endpoints.GET_RECENT_ALBUMS, {withCredentials: true})
        .map(this.extractData)
        .catch(this.handleError);
    }

    getUserPlaylists(){
        return this.http.get<BucciResponse<Playlist[]>>(BucciConstants.Endpoints.GET_USER_PLAYLISTS , {withCredentials: true})
        .map(bucci =>{
            if (bucci.successful){
                this.userPlaylists.next(bucci.response);
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

    addSongToPlaylists(playlist: Playlist, song : Song){
        
        let playlistInfo = this.constructPlaylistUpdate(playlist, song,null);
        console.log(playlistInfo);
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.UPDATE_PLAYLIST, playlistInfo, {withCredentials: true}).
        map(this.extractData)
        .catch(this.handleError);


    }

    removeSongFromPlaylists(playlist: Playlist, song : Song){
        
        let playlistInfo = this.constructPlaylistUpdate(playlist, null,song);
        console.log(playlistInfo);
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.UPDATE_PLAYLIST, playlistInfo, {withCredentials: true}).
        map(this.extractData)
        .catch(this.handleError);


    }

    followPlaylist(playlist: Playlist){
        return this.http.post<BucciResponse<String>>(BucciConstants.Endpoints.FOLLOW_PLAYLIST, playlist, {withCredentials: true}).
        map(this.extractData)
        .catch(this.handleError);

    }

    deletePlaylist(playlist : Playlist){

        let playlistId = playlist.id;
        let requestUrl = BucciConstants.Endpoints.DELETE_PLAYLIST + "/" + playlistId;

        return this.http.delete<BucciResponse<String>>(requestUrl,{
           withCredentials: true
        }).
        map(bucci  => {
                if(bucci.successful){
                    this.reloadPlaylists();
                    return bucci.response;
                }
                else{
                    throw new Error(bucci.message);
                }
                
            }).catch((error : any) =>{
                return Observable.throw(new Error(error.message));
            });

    }

    private reloadPlaylists(){
        this.getUserPlaylists().subscribe(
            (data) =>{console.log("updated playlists")}
        )
    }

    private constructPlaylistUpdate(playlist : Playlist, acceptedSong: Song, removedSong) : PlaylistPage{
        let add = acceptedSong ? [acceptedSong] : null;
        let remove = removedSong ? [removedSong] : null;
        let playlistInfo = new PlaylistPage();
        playlistInfo.playlist = playlist;
        playlistInfo.playlist.songs = null;
        playlistInfo.playlist.owner = null;
        playlistInfo.songsToAdd = add;
        playlistInfo.songsToRemove= remove;
        return playlistInfo;
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
