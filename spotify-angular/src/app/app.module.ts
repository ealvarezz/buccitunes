import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MdToolbarModule, MdButtonModule, MdDialogModule, MdIconModule, MdSliderModule,MdProgressBarModule, MdGridListModule, MdSidenavModule, MdListModule, MdCardModule,MdInputModule, MdStepperModule,MdRadioModule,MdSelectModule, MdTabsModule, MdTableModule, MdMenuModule, MdCheckboxModule, MdTooltipModule, MD_DIALOG_DATA, MdDialogRef,MdSlideToggleModule,MdChipsModule, MdExpansionModule} from '@angular/material';
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
import {RequestedAlbumTableComponent} from './request-table.component';
import {DetailDialog} from './detail-dialog.component';
import {RequestedAlbumComponent } from './requested-album.component';
import {AddAlbumDialog} from './artist.component'
import {AuthenticationService} from './services/authentication.service'
import { DatePipe } from '@angular/common';
import {UserTableComponent} from './user-table.component';
import { HttpModule } from '@angular/http';
import {HttpClientModule} from '@angular/common/http';
import { AuthGuard } from './services/AuthGuard';
import {ArtistService} from './services/artist.service';
import {MusicCollectionService} from './services/music.service';
import {AddPlaylistDialog} from './add-playlist.component';
import {DomSanitizer} from '@angular/platform-browser';
import { SimpleNotificationsModule } from 'angular4-notifications';
import {SongLibrary} from './song-library.component'
import {AlbumLibrary} from './saved-albums.component';
import {AdminService} from './services/admin.service';
import {AccountSettingsComponent}  from './account-settings.component';
import {ReceiptTableComponent} from './receipt-table';


const appRoutes: Routes = [
  { path: '', component: MainComponent, 
        children:[
          { path: '', component: HomePageComponent, canActivate: [AuthGuard] },
          {path: 'album/:id', component: AlbumComponent, canActivate: [AuthGuard]},
          {path: 'playlist', component: PlaylistComponent, canActivate: [AuthGuard]},
          {path: 'artist/:id', component: ArtistComponent, canActivate: [AuthGuard]},
          {path: 'admin',component:AdminComponent, canActivate: [AuthGuard], 
              children:[
                {path: 'requested_albums', component: RequestedAlbumComponent, canActivate: [AuthGuard]}
              ]},
          {path: 'library', component: SongLibrary, canActivate: [AuthGuard]},
          {path: 'album-library', component: AlbumLibrary, canActivate: [AuthGuard]},
          {path: 'account-settings', component: AccountSettingsComponent, canActivate: [AuthGuard]},
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
    RequestedAlbumTableComponent,
    DetailDialog,
    AddAlbumDialog,
    AddPlaylistDialog,
    SongLibrary,
    AlbumLibrary,
    RequestedAlbumComponent,
    UserTableComponent,
    AccountSettingsComponent,
    ReceiptTableComponent
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
    MdChipsModule,
    HttpClientModule,
    MdExpansionModule,
    SimpleNotificationsModule.forRoot()
  ],
  providers: [
    MusicService,
    AuthenticationService,
    ArtistService,
    MusicCollectionService,
    AdminService,
    AuthGuard,
    { provide: MD_DIALOG_DATA, useValue: {} },
    { provide: MdDialogRef, useValue: {} }
  ],
  entryComponents: [
    DetailDialog,
    AddAlbumDialog,
    AddPlaylistDialog
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
