import { Component, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import {MusicCollectionService } from './services/music.service'
import {SpinnerService} from './services/spinner.service';

@Component({
  selector: 'main-page',
  templateUrl: '../views/main.component.html'
})
export class MainComponent {


  constructor(private musicService : MusicCollectionService,
              private spinnerService : SpinnerService,
            private _changeDetectionRef : ChangeDetectorRef){
    }

    showSpinner : boolean = false;

    ngOnInit(){
      this.getPlaylists();
    }

    ngAfterViewInit() : void {

        this.spinnerService.isLoading.subscribe(
            (loading) => {
                this.showSpinner = loading;
                this._changeDetectionRef.detectChanges();
            }
        );
        
    }

    getPlaylists(){
        this.musicService.getUserPlaylists().subscribe(
            (data) =>{
                console.log("updated playlist");
            }
        )
    }



}
