import { Component, Input } from '@angular/core';
import {BrowseItem, BrowseList} from './objs/Browse';

@Component({
  selector: 'homepage-template',
  templateUrl: '../views/homepage-template.component.html'
})
export class HomePageTabTemplate {
    @Input() data : BrowseList[];
    @Input() rowHeight : number = 300; 

    // ngOnInit(){
    //     this.rowHeight = this.rowHeight !=null? this.rowHeight : 300;
    // }
}



