import { Component } from '@angular/core';
import { Router } from "@angular/router"; 
import {MdDialog, MdDialogRef} from '@angular/material';

import {Playlist} from './objs/Playlist';
import {MusicCollectionService } from './services/music.service';

@Component({
  selector: 'side-bar',
  templateUrl: '../views/sidebar.component.html',
  styleUrls: ['../views/styles/sidebar.component.css']
})
export class SideBarComponent {
    constructor(public dialog: MdDialog, private router: Router) {}

    logout() {
        // Do Stuff for logging out
        this.router.navigate(["/login"]);
    }

    addPlaylist(){
        let dialogRef = this.dialog.open(AddPlaylistDialog);
    }

    savedPlaylists : PlaylistTemp[] = [new PlaylistTemp("Sajid's Playlist","own"), new PlaylistTemp("DICS Playlist","public"), new PlaylistTemp("The Block Playlist","friend"), new PlaylistTemp("Justin's Playlist","friend"), new PlaylistTemp("Chris' Playlist","friend")] 
    recentPlaylists: string[] = ["DAMN","Coloring Book", "Gym Playlist", "Billboard Hot 100", "RapCaviar", "Signed XOXO", "Graduation","Power Workout", "Channel X"];
}

@Component({
  selector: 'add-playlist',
  templateUrl: '../views/add-playlist.html',
  styleUrls:["../views/styles/add-playlist.css"]
})
export class AddPlaylistDialog {

  constructor(public dialogRef: MdDialogRef<AddPlaylistDialog>, private musicService : MusicCollectionService) {
     }

     name : string;

     closeDialog(){
         this.dialogRef.close();
     }
    
     addPlaylist(){
         let playlist = new Playlist();
         playlist.title = this.name;
         playlist.public = false;
         playlist.collaborative = false;
        this.musicService.addPlaylist(playlist)
            .subscribe(
                (data) => {
                    this.closeDialog();
                    console.log(data);
                },
                (err) => {
                    console.log(err.message);
                });
     }
}

export class PlaylistTemp {
    name: string;
    source: string;

    constructor(private playlistName: string, private playlistSource: string) {
        this.name = playlistName;
        this.source = playlistSource;
    }

}