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
import {SpinnerService} from './services/spinner.service';
import {MusicCollectionService} from './services/music.service';
import { Observable } from 'rxjs/Rx';
import {NotificationsService} from 'angular4-notifications';
import {AuthenticationService} from './services/authentication.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {MediaService} from './services/media.service';
import {Location} from '@angular/common';
import {BucciConstants} from '../environments/app.config';
import 'rxjs/add/observable/forkJoin';
import {AddConcertDialog} from './add-concert';
import {Concert} from './objs/Concert';
import {ConcertDetailComponent} from './concert-detail-dialog';


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
  isOwner       : boolean = false;
  following     : boolean = false;
  bio           : string;
  artwork       : string;
  relatedArtists  : Artist[];
  latestAlbum : Album;

  constructor(public dialog                 : MdDialog,
              private artistService         : ArtistService,
              private mediaService          : MediaService,
              private route                 : ActivatedRoute,
              private location              : Location,
              private notificationService   : NotificationsService,
              private authenticationService : AuthenticationService,
              private spinnerService        : SpinnerService){}

  ngOnInit(){
    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.loadArtist(id);

        // this.getArtist(+params['id']);
    });

    this.authenticationService.currentUserChange.subscribe(
      (user) => {
        this.currentUser = user;
      }
    );
    
  }

  loadArtist(id : number){
    this.spinnerService.openSpinner();
    Observable.forkJoin(
      this.artistService.getArtist(id),
      this.artistService.getConcerts(id),
      this.artistService.getRelatedArtists(id),
    ).subscribe(
      (res)=>{
        this.artist = res[0];
        this.bio = this.artist.biography;
        this.artist.avatarPath = this.artist.avatarPath+'?dummy='+new Date().getTime();

        this.artist.concerts = res[1];
        this.relatedArtists = res[2];
        this.isCurrentUserOwner();
        this.getLatestAlbum();
        this.spinnerService.stopSpinner();
    },
      (err)=>{
        this.notificationService.error("ERROR","There was an error loading this artist");
        this.spinnerService.stopSpinner();
      }
    );
  }
  // getArtist(id : number ){
  //  this.artistService.getArtist(id)
  //     .subscribe(
  //         (data) => {
  //           this.artist = data;
  //           this.isCurrentUserOwner();
  //           this.getConcerts();
  //           this.getRelatedArtists();
  //           this.bio = this.artist.biography;
  //           this.artist.avatarPath = this.artist.avatarPath+'?dummy='+new Date().getTime();
  //         },  
  //         (err) => {
  //           this.notificationService.error("ERROR","There was an error loading this artist.");
  //           this.location.back();
  //           console.log(err.message);
  //         });
  // }


  // getConcerts(){
  //   this.artistService.getConcerts(this.artist.id).subscribe(
  //     (data)=>{
  //       this.artist.concerts = data;
  //     },
  //     (err)=>{
  //       this.notificationService.error("ERROR","There was an error fetching concerts");
  //     }
  //   )
  // }

  // getRelatedArtists(){
  //   this.artistService.getRelatedArtists(this.artist.id).subscribe(
  //     (data)=>{
  //       this.relatedArtists = data;
  //     },
  //     (err)=>{
  //       this.notificationService.error("ERROR","There was an error fetching related artists");
  //     }
  //   )
  // }
  
  editBio(){
    this.artist.biography = this.bio;
    this.artist.avatar = this.artwork ? this.mediaService.trimImageBase64(this.artwork) : null;
    this.artwork = null;
    this.artistService.updateArtist(this.artist).subscribe(
      (data)=>{
        this.toggleEditModeBio();
        this.loadArtist(this.artist.id);
        this.notificationService.success("Success","Artist successfully updated.");
      },
      (err)=>{
        this.notificationService.error("Error", "There was an error updating this artist");
      }
    )
  }

  getLatestAlbum(){
    if(this.artist.albums.length > 0){
      let recent = this.artist.albums[0];
      for(let album of this.artist.albums ){
        if(album.releaseDate > recent.releaseDate){
          recent = album;
        }
      }

      this.latestAlbum = recent;
    }
    
  }

  uploadImage(event){
    this.mediaService.previewImage(event).subscribe(
           (data : MediaFile)=>{
                this.artwork = data.artwork;
                //this.albumArtworkPath = data.artworkPath;
           },
          (err) =>{
              // this.artworkError.status = true;
              // this.artworkError.message = err.message;
            });
  }


  viewDetails(concert : Concert){
    let dialogRef = this.dialog.open(ConcertDetailComponent, {
      width: '1000px',
      data: {
        concert: concert
      }
    });

  }



  followArtist(){
    this.artistService.followArtist(this.artist).subscribe(
      (data)=>{
        this.artist.following = true;
      },
      (err)=>{
        this.notificationService.error("ERROR", "There was an issue following this artist.");
      }
    )
  }

  unFollowArtist(){
    this.artistService.unFollowArtist(this.artist).subscribe(
      (data)=>{
        this.artist.following = false;
      },
      (err)=>{
        this.notificationService.error("ERROR", "There was an issue unfollowing this artist.");
      }
    );
  }

  isCurrentUserOwner(){
    if((this.currentUser.role === BucciConstants.Roles.ARTIST) && (this.artist) && (this.currentUser.artist.id == this.artist.id)){
      this.isOwner = true;
    }
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
        this.loadArtist(this.artist.id)
        this.notificationService.success("SUCCESS",result);
      }
    });
  }

  addConcert(){
    let dialogRef = this.dialog.open(AddConcertDialog, {width:'1200px'});

    dialogRef.afterClosed().subscribe((result : Concert) => {
      if(result){
        result.mainStar = this.artist;
        this.artistService.requestConcert(result).subscribe(
          (data)=>{
            this.notificationService.success("SUCCESS","You have successfully requested a concert. Please allow 4-5 hours for an admin to review and approve this concert.");
          },
          (err)=>{
            this.notificationService.error("ERROR","There was a problem submitting your concert. Please try again or contact an admin for further assistance.")
          }
        )

      }
    });
  }


  assignAlbums(albums : Album[]) : Observable<boolean>{
    for( let album of albums){
      for(let song of album.songs){
        song.album = album;
        song.owner = this.artist;
      }
    }
    return Observable.of(true);
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
    if(!this.isEditModeBio){
      this.artwork = null;
    }
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