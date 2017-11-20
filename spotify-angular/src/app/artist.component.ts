import { Component } from '@angular/core';
import {PlayerComponent} from './player.component'
import {Song} from './objs/Song';
import {Album} from './objs/Album';
import {MdDialog, MdDialogRef} from '@angular/material';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MdChipInputEvent, ENTER} from '@angular/material';

@Component({
  selector: 'artist-page',
  templateUrl: '../views/artist.component.html',
  styleUrls: ['../views/styles/artist.component.css']
})
export class ArtistComponent {
  songs : Song[] =  [new Song(), new Song(), new Song(), new Song(), new Song(), new Song() , new Song() , new Song() , new Song() , new Song()];
  viewSongs : Song[] = this.songs.slice(0,5);
  showAllSongs : boolean = false;

  isEditModeBio : boolean = false;

  constructor(public dialog: MdDialog){}

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

  constructor(public dialogRef: MdDialogRef<AddAlbumDialog>, private _formBuilder: FormBuilder) {
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
        this.currentAlbum.artwork = fileList[0];

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


}