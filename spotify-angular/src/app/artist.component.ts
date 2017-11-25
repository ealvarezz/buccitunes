import { Component, OnInit } from '@angular/core';

import {Song} from './objs/Song';
import {Album} from './objs/Album';
import {RequestedAlbum} from './objs/RequestedAlbum';
import {Artist} from './objs/Artist';

import {MdDialog, MdDialogRef} from '@angular/material';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MdChipInputEvent, ENTER} from '@angular/material';

import {ArtistService} from './services/artist.service';
import {MusicCollectionService} from './services/music.service';

import { Observable } from 'rxjs/Rx';

import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'artist-page',
  templateUrl: '../views/artist.component.html',
  styleUrls: ['../views/styles/artist.component.css']
})
export class ArtistComponent implements OnInit {

  artist : Artist;

  songs : Song[] =  [new Song(), new Song(), new Song(), new Song(), new Song(), new Song() , new Song() , new Song() , new Song() , new Song()];
  viewSongs : Song[] = this.songs.slice(0,5);
  showAllSongs : boolean = false;
  isEditModeBio : boolean = false;

  constructor(public dialog: MdDialog, private artistService : ArtistService, private route: ActivatedRoute){}

  ngOnInit(){
    this.route.params.subscribe(params => {
        this.getArtist(+params['id']);
    });
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
    let dialogRef = this.dialog.open(AddAlbumDialog);
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

  previewImage(event){
    let fileList: FileList = event.target.files;
    let file = fileList[0].name;

    if(fileList.length > 0) {
        let file = fileList[0];

        let reader = new FileReader();

        reader.onload = (e: any) => {
            let file = e.target.result;
        }

        reader.readAsDataURL(fileList[0]);
    }
  }


}

@Component({
  selector: 'add-album',
  templateUrl: '../views/add-album.html',
  styleUrls:["../views/styles/add-album.css"]
})
export class AddAlbumDialog {

  constructor(public dialogRef: MdDialogRef<AddAlbumDialog>, private _formBuilder: FormBuilder, private musicService : MusicCollectionService) {
     }

  infoFormGroup: FormGroup;
  artworkFormGroup: FormGroup;
  songsFormGroup : FormGroup;
  months : string[] = ['January','Feburary','March','April','May','June','July','August','September','October','November','Decemeber']

  currentAlbum : Album = new Album();

  songs : Song[] = [];
  albumArtworkPath : string = '';
  albumArtwork : any;


  visible: boolean = true;
  selectable: boolean = true;
  removable: boolean = true;
  addOnBlur: boolean = true;
    
  releaseMonth: string;
  releaseDay: number;
  releaseYear: number;

  ngOnInit() {
    this.currentAlbum.featuredArtists = ['R Kelly', 'Lil Jon', 'Lil B','Big Shaq'];
    this.infoFormGroup = this._formBuilder.group({
      title: ['', Validators.required],
      dayCtrl: ['', Validators.max(31)],
      yearCtrl:['',Validators.max((new Date()).getFullYear() + 1)]
    });
    this.artworkFormGroup = this._formBuilder.group({
    });
    this.songsFormGroup= this._formBuilder.group({
      song_title: ['',Validators.required]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addNewSong(){
    this.currentAlbum.songs.push(new Song());
  }
  
  removeSong(song: Song){
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
      this.currentAlbum.featuredArtists.push(value.trim());
    }
    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  previewImage(event){
    let fileList: FileList = event.target.files;
    this.albumArtworkPath = fileList[0].name;

    if(fileList.length > 0) {
        // this.currentAlbum.artwork = fileList[0];

        let reader = new FileReader();

        reader.onload = (e: any) => {
            this.currentAlbum.artwork = e.target.result;
        }

        reader.readAsDataURL(fileList[0]);
    }
  }

  remove(artist: any): void {
    let index = this.currentAlbum.featuredArtists.indexOf(artist);

    if (index >= 0) {
      this.currentAlbum.featuredArtists.splice(index, 1);
    }
  }

  setDate(){
    this.currentAlbum.releaseDate.setMonth(this.months.indexOf(this.releaseMonth));
    this.currentAlbum.releaseDate.setDate(this.releaseDay);
    this.currentAlbum.releaseDate.setFullYear(this.releaseYear);
  }

  submitAlbum(){

    this.musicService.addAlbum(this.currentAlbum)
            .subscribe(
                (data) => {
                    console.log(data);
                    this.dialogRef.close();
                },
                (err) => {
                    console.log(err.message);
                });

  }


}