import { Component } from '@angular/core';
import { Router } from "@angular/router"; 

@Component({
  selector: 'side-bar',
  templateUrl: '../views/sidebar.component.html',
  styleUrls: ['../views/styles/sidebar.component.css']
})
export class SideBarComponent {
    constructor(private router: Router) {}

    logout() {
        // Do Stuff for logging out
        this.router.navigate(["/login"]);
    }

    savedPlaylists : Playlist[] = [new Playlist("Sajid's Playlist","own"), new Playlist("DICS Playlist","public"), new Playlist("The Block Playlist","friend"), new Playlist("Justin's Playlist","friend"), new Playlist("Chris' Playlist","friend")] 
    recentPlaylists: string[] = ["DAMN","Coloring Book", "Gym Playlist", "Billboard Hot 100", "RapCaviar", "Signed XOXO", "Graduation","Power Workout", "Channel X"];
}

export class Playlist {
    name: string;
    source: string;

    constructor(private playlistName: string, private playlistSource: string) {
        this.name = playlistName;
        this.source = playlistSource;
    }

}