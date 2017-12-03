import { Component, OnInit, Inject } from '@angular/core';
import { environment } from '../environments/environment';
import {Song} from './objs/Song';
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

@Component({
  selector: 'artist-page',
  templateUrl: '../views/artist.component.html',
  styleUrls: ['../views/styles/artist.component.css']
})
export class ArtistComponent implements OnInit {

  artist        : Artist;
  currentUser      : User;
  songs         : Song[] =  [new Song(), new Song(), new Song(), new Song(), new Song(), new Song() , new Song() , new Song() , new Song() , new Song()];
  viewSongs     : Song[] = this.songs.slice(0,5);
  showAllSongs  : boolean = false;
  isEditModeBio : boolean = false;

  constructor(public dialog: MdDialog, private artistService : ArtistService, private route: ActivatedRoute, private notificationService : NotificationsService, private authenticationService : AuthenticationService){}

  ngOnInit(){
    this.route.params.subscribe(params => {
        this.getArtist(+params['id']);
    });

    this.currentUser = this.authenticationService.getLoggedInUser();
  }
  getArtist(id : number ){
   this.artistService.getArtist(id)
      .subscribe(
          (data) => {
                    this.artist = data;
                  },  
          (err) => {
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

  constructor(public dialogRef: MdDialogRef<AddAlbumDialog>, @Inject(MD_DIALOG_DATA) public data: any,  private _formBuilder: FormBuilder, private musicService : MusicCollectionService) {
     }

  infoFormGroup     : FormGroup;
  artworkFormGroup  : FormGroup;
  songsFormGroup    : FormGroup;
  months            : string[] = ['January','Feburary','March','April','May','June','July','August','September','October','November','Decemeber']
  currentAlbum      : RequestedAlbum;
  artist            : Artist;
  user              : User;
  albumArtworkPath  : string = '';
  releaseMonth      : string;
  releaseDay        : number;
  releaseYear       : number;


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
      dayCtrl: ['', [Validators.max(31), Validators.min(1)]],
      yearCtrl:['',Validators.max((new Date()).getFullYear() + 1)]
    });

    this.songsFormGroup= this._formBuilder.group({
      song_title: ['']
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

  previewImage(event){
    let fileList: FileList = event.target.files;
    let file = fileList[0];

    if(fileList.length > 0) {

        let reader = new FileReader();

        reader.onload = (e: any) => {
          if(this.validateImage(file)){
            this.currentAlbum.artwork = e.target.result;
            this.albumArtworkPath = file.name;
          }
          else{
            this.artworkError.status = true;
            this.artworkError.message = environment.EXCEED_FILE_LIMIT;
          }   
        }

        reader.readAsDataURL(file);
    }
  }

  validateImage(image: File){
      if(image.size > environment.IMAGE_SIZE_LIMIT){
          return false;
      }
      else{
        return true;
      }
  }

  remove(artist: any): void {
    // let index = this.currentAlbum.featuredArtists.indexOf(artist);

    // if (index >= 0) {
    //   this.currentAlbum.featuredArtists.splice(index, 1);
    // }
  }

  setDate(){
    this.currentAlbum.releaseDate.setMonth(this.months.indexOf(this.releaseMonth));
    this.currentAlbum.releaseDate.setDate(this.releaseDay);
    this.currentAlbum.releaseDate.setFullYear(this.releaseYear);
  }


  submitAlbum(){
    this.currentAlbum.primaryArtist = this.artist;
    this.currentAlbum.artwork = this.currentAlbum.artwork.split(",")[1];

    if(this.user.role === environment.ARTIST_ROLE){
      this.submitAlbumArtist();
    }
    else{
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


}