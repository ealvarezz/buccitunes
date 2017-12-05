import { Injectable, Inject} from '@angular/core';
import { environment } from '../../environments/environment';
import {BucciConstants} from '../../environments/app.config';
import { Observable } from 'rxjs/Observable';
import { catchError, tap }               from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class SpinnerService{

    constructor() { }

    isLoading : BehaviorSubject<boolean> = new BehaviorSubject(false);


    openSpinner(){
        this.isLoading.next(true);
    }

    stopSpinner(){
        this.isLoading.next(false);
    }
}
