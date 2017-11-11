import { Component, Input } from '@angular/core';


@Component({
  selector: 'image',
  templateUrl: '../views/image.component.html',
  styleUrls: ["../views/styles/image.component.css"]
})
export class ImageComponent{
 @Input() src : String;
 showOverlay : boolean = false;

 toggleOverlay(){
     this.showOverlay = !this.showOverlay;
 }

}