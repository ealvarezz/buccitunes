import { Component, Input, Output, OnInit, OnChanges, SimpleChanges , EventEmitter,ElementRef, ViewChild} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import { User } from './objs/User'
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/observable/fromEvent';

@Component({
  selector: 'user-table',
  templateUrl: '../views/user-table.component.html',
  styleUrls: ["../views/styles/user-table.component.css"]
})
export class UserTableComponent implements OnChanges {
    @Input() users : User[];
    @Output() selectedRow: EventEmitter<User> = new EventEmitter();
    @ViewChild('filter') filter: ElementRef;
    dataSource : UserDataSource;
    hoveredRow : number = -1;
    displayedColumns = ['username','email', 'type','status','join_date'];

    ngOnChanges(change : SimpleChanges){
      if(change['users'] && !change['users'].isFirstChange()){
        this.users = change['users'].currentValue;
        this.dataSource.users.next(this.users);
      }
    }

    selectRow(row){
      this.selectedRow.emit(row);
    }
    ngOnInit(){
        this.dataSource = new UserDataSource(this.users);
        Observable.fromEvent(this.filter.nativeElement, 'keyup')
        .debounceTime(150)
        .distinctUntilChanged()
        .subscribe(() => {
          if (!this.dataSource) { return; }
          this.dataSource.filter = this.filter.nativeElement.value;
        });
    }

    

    hover(i){
      this.hoveredRow = i;
    }
    unHover(){
      this.hoveredRow = -1;
    }
}




export class UserDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  users : BehaviorSubject<User[]> = new BehaviorSubject([]);

   get data(): User[] { return this.users.value; }

   _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }
  constructor(private dat: User[]){
      super()
      this.users.next(dat)
  }

  connect(): Observable<User[]> {
    const displayDataChanges = [
      this.users,
      this._filterChange,
    ];
    return Observable.merge(...displayDataChanges).map(() => {
      return this.data.slice().filter((item: User) => {
        let searchStr = (item.email + item.username).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });
    });
  }

  disconnect() {}
}