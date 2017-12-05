import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {MediaFile} from '../objs/MediaFile';
import {environment} from '../../environments/environment';
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
                observer.error(new Error(environment.EXCEED_FILE_LIMIT));
            }   
            }
        });  
        return fileObservable;
    }
    
  }

  validateImage(image: File){
      if(image.size > environment.IMAGE_SIZE_LIMIT){
          return false;
      }
      else{
        return true;
      }
  }


 getAlbumImageUrl(imagePath : string){
     if(imagePath){
         return environment.SERVER_PATH + imagePath;
     }
    else{
        return environment.LOCAL_RESOURCE + environment.DEFAULT_ALBUM_ARTWORK;
    }
  }

  getPlaylistImageUrl(imagePath : string){
      if(imagePath){
         return environment.SERVER_PATH + imagePath;
     }
    else{
        return environment.LOCAL_RESOURCE + environment.DEFAULT_ARTWORK;
    }
  }

  getUserImageUrl(imagePath: string){
      if(imagePath){
          return environment.SERVER_PATH + imagePath;
      }
      else{
          return environment.LOCAL_RESOURCE + environment.DEFAULT_USER_ARTWORK;
      }
  }

}