import { Component, Input, OnInit} from '@angular/core';
import {Song} from './objs/Song'
import {environment} from '../environments/environment';
import {NotificationsService} from 'angular4-notifications';
import {QueueService} from './services/queue.service'



@Component({
  selector: 'queue-component',
  templateUrl: '../views/queue.component.html',
  styleUrls: ["../views/styles/album.component.css"]
})
export class QueueComponent implements OnInit{

    constructor(private queueService : QueueService){}

    nowPlaying : Song[] = [];
    queue : Song[] = [];
    history : Song[];

    ngOnInit(){
        this.queueService.musicQueueChange.subscribe(
            (data)=>{
                if(data.length > 0){
                    this.nowPlaying = [data[0]];
                    this.queue = data.slice(1, data.length);
                }
                else{
                    this.nowPlaying = [];
                    this.queue = data;
                }
            }
        );

        this.queueService.historyChange.subscribe(
            (data) =>{
                this.history = data;
            }
        )

    }

    clearQueue(){
        this.queueService.clearQueue();
    }

}