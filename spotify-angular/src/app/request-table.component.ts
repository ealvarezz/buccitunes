import { Component, Input, Output, OnInit, OnChanges, SimpleChanges,EventEmitter } from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';

@Component({
  selector: 'request-table',
  templateUrl: '../views/request-table.component.html',
  styleUrls: ["../views/styles/request-table.component.css"]
})
export class RequestTableComponent{
    @Input() requests : any[];
    @Output() openDialog: EventEmitter<any> = new EventEmitter();
    displayedColumns = ['checkBox','requested_content', 'artist','created_by','requested_date','details','decline'];
    dataSource : RequestDataSource;
    hoveredRow : number = -1;

    dialog(row){
      this.openDialog.emit(row);
    }
    ngOnInit(){
        this.dataSource = new RequestDataSource(this.requests);
    }
    
    ngOnChanges(change : SimpleChanges){
      if(change['requests'] && !change['requests'].isFirstChange()){
        this.requests = change['requests'].currentValue;
        this.dataSource.requests.next(this.requests);
      }
    }

    hover(i){
      this.hoveredRow = i;
    }
    unHover(){
      this.hoveredRow = -1;
    }
}

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