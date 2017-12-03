import { Component, Input, Output, OnInit, OnChanges, SimpleChanges,EventEmitter } from '@angular/core';
import {RequestDataSource} from './request-data-source';
import {Payment} from './objs/Payment';
@Component({
  selector: 'receipt-table',
  templateUrl: '../views/receipt-table.component.html'
})
export class ReceiptTableComponent{
    @Input() receipts : Payment[];
    displayedColumns = ['date','transaction_id', 'price','view'];
    dataSource : RequestDataSource;
    hoveredRow : number = -1;

    ngOnInit(){
        this.dataSource = new RequestDataSource(this.receipts);
    }
    
    ngOnChanges(change : SimpleChanges){
      if(change['receipts'] && !change['receipts'].isFirstChange()){
        this.receipts = change['receipts'].currentValue;
        this.dataSource.requests.next(this.receipts);
      }
    }

    hover(i){
      this.hoveredRow = i;
    }
    unHover(){
      this.hoveredRow = -1;
    }
}