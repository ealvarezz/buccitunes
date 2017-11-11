import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MdToolbarModule, MdButtonModule, MdIconModule, MdSliderModule,MdProgressBarModule, MdGridListModule, MdSidenavModule, MdListModule, MdCardModule,MdInputModule, MdStepperModule,MdRadioModule,MdSelectModule, MdTabsModule, MdTableModule, MdMenuModule} from '@angular/material';
import {PlayerComponent} from './player.component'
import {HomePageTabTemplate} from './homepage-tab.component'
import { AppComponent } from './app.component';
import {SideBarComponent} from './sidebar.component'
import {HomePageComponent} from './homepage.component'
import {MainComponent} from './main.component'
import {LoginComponent} from './login.component'
import {NavComponent} from './nav.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { SignUpComponent } from './sign-up.component'
import {ImageComponent } from './image.component';
import {MusicTableComponent} from './music-table.component';
import {AlbumComponent} from './album.component';
import {CdkTableModule} from '@angular/cdk/table';
import {PlaylistComponent} from './playlist.component';
import {MusicService} from './services/player.service';
import {ArtistComponent} from './artist.component';
import {MiniAlbumComponent} from './mini-album.component';


const appRoutes: Routes = [
  { path: '', component: MainComponent, 
        children:[
          { path: '', component: HomePageComponent },
          {path: 'album', component: AlbumComponent},
          {path: 'playlist', component: PlaylistComponent},
          {path: 'artist', component: ArtistComponent}
        ] 
  },
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: SignUpComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    PlayerComponent,
    SideBarComponent,
    MainComponent,
    HomePageComponent,
    HomePageTabTemplate,
    LoginComponent,
    SignUpComponent,
    NavComponent,
    ImageComponent,
    MusicTableComponent,
    AlbumComponent,
    PlaylistComponent,
    ArtistComponent,
    MiniAlbumComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes
    ),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MdToolbarModule,
    MdButtonModule, 
    MdIconModule,
    MdProgressBarModule,
    MdSliderModule,
    MdSidenavModule,
    MdCardModule,
    MdListModule,
    MdGridListModule,
    MdInputModule,
    MdStepperModule,
    MdRadioModule,
    MdSelectModule,
    MdTabsModule,
    MdTableModule,
    CdkTableModule,
    MdMenuModule
  ],
  providers: [MusicService],
  bootstrap: [AppComponent]
})
export class AppModule { }
