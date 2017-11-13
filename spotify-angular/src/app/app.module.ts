import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MdToolbarModule, MdButtonModule, MdDialogModule, MdIconModule, MdSliderModule,MdProgressBarModule, MdGridListModule, MdSidenavModule, MdListModule, MdCardModule,MdInputModule, MdStepperModule,MdRadioModule,MdSelectModule, MdTabsModule, MdTableModule, MdMenuModule, MdCheckboxModule, MdTooltipModule, MD_DIALOG_DATA, MdDialogRef,MdSlideToggleModule,MdChipsModule} from '@angular/material';
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
import {AdminComponent} from './admin.component';
import {RequestTableComponent} from './request-table.component';
import {DetailDialog} from './admin.component';
import {AddAlbumDialog} from './artist.component'
import {AuthenticationService} from './services/authentication.service'
  import { DatePipe } from '@angular/common';
  import {UserTableComponent} from './user-table.component';
import { HttpModule } from '@angular/http';

import { AuthGuard } from './services/AuthGuard';


const appRoutes: Routes = [
  { path: '', component: MainComponent, 
        children:[
          { path: '', component: HomePageComponent, canActivate: [AuthGuard] },
          {path: 'album', component: AlbumComponent, canActivate: [AuthGuard]},
          {path: 'playlist', component: PlaylistComponent, canActivate: [AuthGuard]},
          {path: 'artist', component: ArtistComponent, canActivate: [AuthGuard]},
          {path: 'admin',component:AdminComponent, canActivate: [AuthGuard]}
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
    MiniAlbumComponent,
    AdminComponent,
    RequestTableComponent,
    DetailDialog,
    AddAlbumDialog,
    UserTableComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes
    ),
    HttpModule,
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
    MdDialogModule,
    MdTabsModule,
    MdTableModule,
    CdkTableModule,
    MdMenuModule,
    MdTooltipModule,
    MdSlideToggleModule,
    MdCheckboxModule,
    MdChipsModule
  ],
  providers: [
    MusicService,
    AuthenticationService,
    AuthGuard,
    { provide: MD_DIALOG_DATA, useValue: {} },
    { provide: MdDialogRef, useValue: {} }
  ],
  entryComponents: [
    DetailDialog,
    AddAlbumDialog
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
