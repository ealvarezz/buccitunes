import { Component } from '@angular/core';
import { Router } from "@angular/router"; 
import {MdDialog, MdDialogRef} from '@angular/material';
import {AuthenticationService} from './services/authentication.service';

import {AddPlaylistDialog} from './add-playlist.component';
import {Playlist} from './objs/Playlist';


@Component({
  selector: 'side-bar',
  templateUrl: '../views/sidebar.component.html',
  styleUrls: ['../views/styles/sidebar.component.css']
})
export class SideBarComponent {
    constructor(public dialog: MdDialog,
                private router: Router,
                private authService : AuthenticationService) {}

    logout() {
        this.authService.logout().subscribe(
            (data) =>{
                this.router.navigate(["/login"]);
            },
            (err) =>{
                console.log("error");
            }
        )
        
    }

    addPlaylist(){
        let dialogRef = this.dialog.open(AddPlaylistDialog);
    }

    savedPlaylists : PlaylistTemp[] = [new PlaylistTemp("Sajid's Playlist","own"), new PlaylistTemp("DICS Playlist","public"), new PlaylistTemp("The Block Playlist","friend"), new PlaylistTemp("Justin's Playlist","friend"), new PlaylistTemp("Chris' Playlist","friend")] 
    recentPlaylists: string[] = ["DAMN","Coloring Book", "Gym Playlist", "Billboard Hot 100", "RapCaviar", "Signed XOXO", "Graduation","Power Workout", "Channel X"];
}

export class PlaylistTemp {
    name: string;
    source: string;

    constructor(private playlistName: string, private playlistSource: string) {
        this.name = playlistName;
        this.source = playlistSource;
    }

}