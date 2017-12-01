import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';

export class RequestDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  requests : BehaviorSubject<any[]> = new BehaviorSubject([]);
  constructor(private data: any[]){
      super()
      this.requests.next(data)
  }

  connect(): Observable<any[]> {
    return this.requests;
  }

  disconnect() {}
}