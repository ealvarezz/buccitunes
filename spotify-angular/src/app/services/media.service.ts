import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {MediaFile} from '../objs/MediaFile';
import {environment} from '../../environments/environment';
import {BucciConstants} from '../../environments/app.config';
// import {User} from '../objs/User';
// import {BucciResponse} from '../objs/BucciResponse';

// import {HttpClient, HttpResponse, HttpParams, HttpErrorResponse} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class MediaService {
    constructor() { }

    previewImage(event) : Observable<MediaFile>{
    let fileList: FileList = event.target.files;
    let file = fileList[0];

    if(fileList.length > 0) {

        let reader = new FileReader();
        reader.readAsDataURL(file);

        let fileObservable = Observable.create((observer: any) => {
        reader.onload = (e: any) => {
            if(this.validateImage(file)){
                observer.next(new MediaFile(e.target.result , file.name))
                observer.complete()
            }
            else{
                observer.error(new Error(BucciConstants.CoverArt.EXCEED_FILE_LIMIT));
            }   
            }
        });  
        return fileObservable;
    }
    
  }

  uploadSong(event) : Observable<MediaFile>{

    let fileList: FileList = event.target.files;
    let file = fileList[0];

    if(fileList.length > 0) {

        let reader = new FileReader();
        reader.readAsDataURL(file);

        let fileObservable = Observable.create((observer: any) => {
        reader.onload = (e: any) => {
            if(this.validateMP3(file)){
                observer.next(new MediaFile(e.target.result , file.name))
                observer.complete()
            }
            else{
                observer.error(new Error(BucciConstants.SongConstants.EXCEED_FILE_LIMIT));
            }   
            }
        });  
        return fileObservable;
    }

  }

  validateImage(image: File){
      if(image.size > BucciConstants.CoverArt.IMAGE_SIZE_LIMIT){
          return false;
      }
      else{
        return true;
      }
  }

  validateMP3(song :File){
      if(song.size > BucciConstants.SongConstants.SONG_SIZE_LIMIT){
          return false;
      }
      else{
        return true;
      }
  }

  trimImageBase64(encodedString : string){
      return encodedString.split(",")[1];
  }


 getAlbumImageUrl(imagePath : string){
     if(imagePath){
         return environment.SERVER_PATH + imagePath;
     }
    else{
        return environment.LOCAL_RESOURCE + BucciConstants.CoverArt.DEFAULT_ALBUM;
    }
  }

  getArtistImageUrl(imagePath : string){
      if(imagePath){
        return environment.SERVER_PATH + imagePath;
      }
      else{
          return environment.LOCAL_RESOURCE + BucciConstants.CoverArt.DEFAULT_ARTIST;
      }
  }

  getPlaylistImageUrl(imagePath : string){
      if(imagePath){
         return environment.SERVER_PATH + imagePath;
     }
    else{
        return environment.LOCAL_RESOURCE + BucciConstants.CoverArt.DEFAULT_PLAYLIST; 
    }
  }

  getUserImageUrl(imagePath: string){
      if(imagePath){
          return environment.SERVER_PATH + imagePath;
      }
      else{
          return environment.LOCAL_RESOURCE + BucciConstants.CoverArt.DEFAULT_USER;
      }
  }

  getAlbumImageBase64(imagePath: string){
    if(imagePath){
        return imagePath;
    }
    else{
        return environment.LOCAL_RESOURCE + BucciConstants.CoverArt.DEFAULT_ALBUM;
    }
  }

}