import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router"; 
import {MdDialog, MdDialogRef} from '@angular/material';
import {AuthenticationService} from './services/authentication.service';
import {MusicCollectionService} from './services/music.service';
import {Album} from './objs/Album';
import {AddPlaylistDialog} from './add-playlist.component';
import {Playlist} from './objs/Playlist';
import {User} from './objs/User';
import {MusicService} from './services/player.service';
import {QueueService} from './services/queue.service';
import { MediaFile } from "./objs/MediaFile";
import { MediaService } from "./services/media.service";
import { environment } from "../environments/environment";
import {BucciConstants} from "../environments/app.config";


@Component({
  selector: "side-bar",
  templateUrl: "../views/sidebar.component.html",
  styleUrls: ["../views/styles/sidebar.component.css"]
})
export class SideBarComponent implements OnInit {
  constructor(
    public dialog: MdDialog,
    private router: Router,
    private authService: AuthenticationService,
    private musicService: MusicCollectionService,
    private playerService: MusicService,
    public mediaService: MediaService,
    private queueService : QueueService,
  ) {}
  recentAlbums: Album[];
  playlists: Playlist[];
  currentUser: User;
  isArtist : boolean = false;
  isAdmin : boolean = false;


  ngOnInit() {
    this.getRecentlyPlayedAlbums();

    this.authService.currentUserChange.subscribe(
      (user) => {
        this.currentUser = user;
        this.isCurrentUserOwner();
        this.isAdminUser();
      }
    );

    this.musicService.userPlaylists.subscribe(
      playlists => (this.playlists = playlists)
    );
  }

  logout() {
    this.playerService.pauseSong();
    this.authService.logout().subscribe(
      data => {
        this.queueService.clearQueue();
        this.router.navigate(["/login"]);
      },
      err => {
        console.log("error");
      }
    );
  }

  isCurrentUserOwner(){
    if((this.currentUser.role === BucciConstants.Roles.ARTIST) && (this.currentUser.artist)){
        this.isArtist = true;
    }
  }

  isAdminUser(){
    if(this.currentUser.role === BucciConstants.Roles.ADMIN){
      this.isAdmin = true;
    }
  }

  getUserPage() {
    this.router.navigate(["/user", this.currentUser.email]);
  }
  getRecentlyPlayedAlbums() {
    this.musicService.getRecentlyPlayedAlbums().subscribe(
      data => {
        this.recentAlbums = data;
      },
      err => {
        console.log("cannot find");
      }
    );
  }

  getPlaylists() {
    this.musicService.getUserPlaylists().subscribe(data => {
      this.playlists = data;
    });
  }

  addPlaylist() {
    let dialogRef = this.dialog.open(AddPlaylistDialog, { width: "700px" });
  }

  savedPlaylists: PlaylistTemp[] = [
    new PlaylistTemp("Sajid's Playlist", "own"),
    new PlaylistTemp("DICS Playlist", "public"),
    new PlaylistTemp("The Block Playlist", "friend"),
    new PlaylistTemp("Justin's Playlist", "friend"),
    new PlaylistTemp("Chris' Playlist", "friend")
  ];
  recentPlaylists: string[] = [
    "DAMN",
    "Coloring Book",
    "Gym Playlist",
    "Billboard Hot 100",
    "RapCaviar",
    "Signed XOXO",
    "Graduation",
    "Power Workout",
    "Channel X"
  ];
}

export class PlaylistTemp {
    name: string;
    source: string;

    constructor(private playlistName: string, private playlistSource: string) {
        this.name = playlistName;
        this.source = playlistSource;
    }

}