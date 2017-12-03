import { Component, Input, Output, OnInit, OnChanges, SimpleChanges,EventEmitter } from '@angular/core';
import {RequestedAlbum} from './objs/RequestedAlbum';
import {RequestDataSource} from './request-data-source';


@Component({
  selector: 'request-table',
  templateUrl: '../views/request-table.component.html',
  styleUrls: ["../views/styles/request-table.component.css"]
})
export class RequestedAlbumTableComponent{
    @Input() requests : RequestedAlbum[];
    @Output() openDialog: EventEmitter<any> = new EventEmitter();
    displayedColumns = ['checkBox','requested_album', 'artist','created_by','requested_date','details','decline'];
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