import { Component, Input, Output, OnInit, OnChanges, SimpleChanges,EventEmitter } from '@angular/core';
import {Concert} from './objs/Concert';
import {RequestDataSource} from './request-data-source';


@Component({
  selector: 'request-concert-table',
  templateUrl: '../views/requested-concert-table.html',
  styleUrls: ["../views/styles/request-table.component.css"]
})
export class RequestedConcertTableComponent{
    @Input() requests : Concert[];
    @Output() openDialog: EventEmitter<any> = new EventEmitter();
    displayedColumns = ['checkBox','requested_concert', 'artist','created_by','requested_date','details','decline'];
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