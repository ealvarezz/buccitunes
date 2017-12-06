import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { environment } from '../environments/environment';
import {Song} from './objs/Song';
import{MediaFile} from './objs/MediaFile';
import {RequestedSong} from './objs/RequestedSong';
import {Album} from './objs/Album';
import {User} from './objs/User';
import {RequestedAlbum} from './objs/RequestedAlbum';
import {Artist} from './objs/Artist';
import {MdDialog, MdDialogRef, MD_DIALOG_DATA} from '@angular/material';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MdChipInputEvent, ENTER} from '@angular/material';
import {ArtistService} from './services/artist.service';
import {MusicCollectionService} from './services/music.service';
import { Observable } from 'rxjs/Rx';
import {NotificationsService} from 'angular4-notifications';
import {AuthenticationService} from './services/authentication.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {MediaService} from './services/media.service';
import {Location} from '@angular/common';


@Component({
  selector: 'artist-page',
  templateUrl: '../views/artist.component.html',
  styleUrls: ['../views/styles/artist.component.css']
})
export class ArtistComponent implements OnInit {

  artist        : Artist;
  currentUser   : User;
  songs         : Song[] =  [new Song(), new Song(), new Song(), new Song(), new Song(), new Song() , new Song() , new Song() , new Song() , new Song()];
  viewSongs     : Song[] = this.songs.slice(0,5);
  showAllSongs  : boolean = false;
  isEditModeBio : boolean = false;

  constructor(public dialog                 : MdDialog,
              private artistService         : ArtistService,
              private route                 : ActivatedRoute,
              private location              : Location,
              private notificationService   : NotificationsService,
              private authenticationService : AuthenticationService){}

  ngOnInit(){
    this.route.params.subscribe(params => {
        this.getArtist(+params['id']);
    });

    this.authenticationService.currentUserChange.subscribe(
     user => this.currentUser = user
    );
    
  }
  getArtist(id : number ){
   this.artistService.getArtist(id)
      .subscribe(
          (data) => {
            this.artist = data;
          },  
          (err) => {
            this.notificationService.error("ERROR","There was an error loading this artist.");
            this.location.back();
            console.log(err.message);
          });
  }

  addAlbum(){
    let dialogRef = this.dialog.open(AddAlbumDialog, 
      {
        data: {
          artist  : this.artist,
          user    : this.currentUser
        }
      });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.getArtist(this.artist.id)
        this.notificationService.success("SUCCESS",result);
      }
    });
  }

  toggleShow(showAllSongs){
    if(showAllSongs){
      this.viewSongs = this.songs.slice();
      this.showAllSongs = true;
    }
    else{
      this.viewSongs = this.songs.slice(0,5);
      this.showAllSongs = false;
    }
  }

  toggleEditModeBio(){
    this.isEditModeBio = !this.isEditModeBio;
  }

}

@Component({
  selector: 'add-album',
  templateUrl: '../views/add-album.html',
  styleUrls:["../views/styles/add-album.css"]
})
export class AddAlbumDialog {

  constructor(public dialogRef: MdDialogRef<AddAlbumDialog>,
              @Inject(MD_DIALOG_DATA) public data: any,
              private _formBuilder: FormBuilder,
              private musicService : MusicCollectionService,
              private mediaService: MediaService,
              private authenticationService : AuthenticationService) {
     }

  infoFormGroup     : FormGroup;
  artworkFormGroup  : FormGroup;
  songsFormGroup    : FormGroup;
  months            : string[] = ['January','Feburary','March','April','May','June','July','August','September','October','November','Decemeber']
  currentAlbum      : RequestedAlbum;
  artist            : Artist;
  user              : User;
  albumArtworkPath  : string = '';

  disableNext : boolean = false;



  artworkError = {
    status : false,
    message : ''
  }

  mdChipOptions = {
    visible     : true,
    selectable  : true,
    removable   : true,
    addOnBlur   : true
  }


  ngOnInit() {

    this.artist = this.data.artist;
    this.user = this.data.user;

    this.currentAlbum = new RequestedAlbum();

    this.infoFormGroup = this._formBuilder.group({
      title: ['', Validators.required],
      dateCtrl: ['', Validators.required]
    });

    this.songsFormGroup= this._formBuilder.group({
    });

    this.artworkFormGroup = this._formBuilder.group({});
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  addNewSong(): void{
   this.currentAlbum.songs.push(new RequestedSong());
   this.currentAlbum.songs = this.currentAlbum.songs.slice();
  }
  
  removeSong(song: RequestedSong): void{
    let index = this.currentAlbum.songs.indexOf(song);
    if (index >= 0) {
      this.currentAlbum.songs.splice(index, 1);
    }
    this.currentAlbum.songs = this.currentAlbum.songs.slice();
  }

  add(event: MdChipInputEvent): void {
    let input = event.input;
    let value = event.value;

    // Add our person
    if ((value || '').trim()) {
      // this.currentAlbum.featuredArtist.push(value.trim());
    }
    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  uploadImage(event){
    this.mediaService.previewImage(event).subscribe(
           (data : MediaFile)=>{
                this.currentAlbum.artwork = data.artwork;
                this.albumArtworkPath = data.artworkPath;
           },
          (err) =>{
              this.artworkError.status = true;
              this.artworkError.message = err.message;
            });
  }

  uploadSong(event, song : Song){
    this.disableNext = true;
    this.mediaService.uploadSong(event).subscribe(
           (data : MediaFile)=>{
                song.audio = data.artwork;
                song.audioPath = data.artworkPath;
                this.disableNext = false;
                // this.albumArtworkPath = data.artworkPath;
           },
          (err) =>{
              this.artworkError.status = true;
              this.artworkError.message = err.message;
              this.disableNext = false;
            });
  }

  remove(artist: any): void {
    // let index = this.currentAlbum.featuredArtists.indexOf(artist);

    // if (index >= 0) {
    //   this.currentAlbum.featuredArtists.splice(index, 1);
    // }
  }

  submitAlbum(){
    this.currentAlbum.primaryArtist = this.artist;
    this.currentAlbum.artwork = this.mediaService.trimImageBase64(this.currentAlbum.artwork);
    this.sanitizeSongs(this.currentAlbum);

    if(this.authenticationService.isArtistUserRole()){
      this.submitAlbumArtist();
    }
    else if (this.authenticationService.isAdminUserRole()){
      this.submitAlbumAdmin();
    }

  }

  submitAlbumArtist(){
    this.musicService.addAlbumArtist(this.currentAlbum)
      .subscribe(
        (data) => {
          console.log(data);
          this.dialogRef.close("This album has been successfully been submitted for further approval. Please allow a 2-3 hours for admin approval. You will receive an email shortly notifiying you of the admin decision.");
        },
        (err) => {
            console.log(err.message);
          });

  }

  submitAlbumAdmin(){
    this.musicService.addAlbumAdmin(this.currentAlbum)
            .subscribe(
                (data) => {
                    console.log(data);
                    this.dialogRef.close("The album has been sucessfully added for the artist "+ this.artist.name);
                },
                (err) => {
                    console.log(err.message);
                });

  }


  private sanitizeSongs(album : RequestedAlbum){
    for(let song of album.songs){
        song.audio = this.mediaService.trimImageBase64(song.audio);
    }
  }


}