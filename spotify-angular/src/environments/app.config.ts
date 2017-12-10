import { InjectionToken } from "@angular/core";
import {environment} from './environment';
import {IAppConfig} from './app.config.interface';

export const BucciConstants =  {

  Endpoints : {
      GET_ARTIST          : environment.SERVER_PATH + "/artist",
      GET_PLAYLIST        : environment.SERVER_PATH + "/playlist",
      LOGIN               : environment.SERVER_PATH + "/login",
      LOGOUT              : environment.SERVER_PATH + "/logout",
      SIGN_UP             : environment.SERVER_PATH + "/signup",
      LOGGED_IN_USER      : environment.SERVER_PATH + "/logged_in",
      GET_ALBUM           : environment.SERVER_PATH + "/album",
      ADD_ALBUM_ADMIN     : environment.SERVER_PATH + "/add_album_admin",
      ADD_ALBUM_ARTIST    : environment.SERVER_PATH + "/request_album",
      ADD_PLAYLIST        : environment.SERVER_PATH + "/new_playlist",
      SAVE_SONG           : environment.SERVER_PATH + "/save_song",
      GET_SONG_LIBRARY    : environment.SERVER_PATH + "/saved_songs",
      SAVE_ALBUM          : environment.SERVER_PATH + "/save_album",
      GET_SAVED_ALBUMS    : environment.SERVER_PATH + "/saved_albums",
      GET_REQ_ALBUMS      : environment.SERVER_PATH + "/requested_albums",
      GET_REQ_ALBUM       : environment.SERVER_PATH + "/requested_album_info",
      GET_REQ_SONGS       : environment.SERVER_PATH + "/requested_album_info",
      APPROVE_ALBUM       : environment.SERVER_PATH + "/approve_album",
      REJECT_ALBUM        : environment.SERVER_PATH + "/disapprove_request_album",
      GET_RECENT_ALBUMS   : environment.SERVER_PATH + "/recently_played",
      GET_USER_PLAYLISTS  : environment.SERVER_PATH + "/user_playlists",
      UPDATE_PLAYLIST     : environment.SERVER_PATH + "/change_playlist",
      FOLLOW_PLAYLIST     : environment.SERVER_PATH + "/follow_playlist",
      DELETE_PLAYLIST     : environment.SERVER_PATH + "/delete_playlist",
      GET_USER            : environment.SERVER_PATH + "/user",
      FOLLOW_USER         : environment.SERVER_PATH + "/follow",
      UNFOLLOW_USER       : environment.SERVER_PATH + "/unfollow",
      RESET_PW_REQUEST    : environment.SERVER_PATH + "/reset_password_send_email",
      GET_RESET_PW        : environment.SERVER_PATH + "/check_reset_password_link",
      RESET_PASSWORD      : environment.SERVER_PATH + "/reset_password",
      GET_ALL_USERS       : environment.SERVER_PATH + "/get_all_users",
      PLAY_SONG           : environment.SERVER_PATH + "/play_song",
      FOLLOW_ARTIST       : environment.SERVER_PATH + "/follow_artist",
      UNFOLLOW_ARTIST     : environment.SERVER_PATH + "/unfollow_artist",
      GO_SECRET_MODE      : environment.SERVER_PATH + "/go_private",
      CHANGE_USER_INFO    : environment.SERVER_PATH + "/change_settings",
      UPDATE_ARTIST       : environment.SERVER_PATH + "/edit_artist_info",
      GET_PAYMENT_HISTORY : environment.SERVER_PATH + "/payment_info",
      CANCEL_PREMIUM      : environment.SERVER_PATH + "/cancel_subscription",
      GET_LYRICS          : environment.SERVER_PATH + "/getSongLyrics",
      SEARCH              : environment.SERVER_PATH + "/search",
      RESET_PASS_NO_MAIL  : environment.SERVER_PATH + "/reset_password_nomail",
      CHANGE_BILLING_INFO : environment.SERVER_PATH + "/change_billing_info"
  },

 Roles : {
    ARTIST  : "ARTIST",
    ADMIN   : "ADMIN",
    PREMIUM : "PREMIUM",
  },

  CoverArt : {
    DEFAULT_PLAYLIST  : "/default-cover-art.png",
    DEFAULT_ALBUM     : "/default-album-artwork.png",
    DEFAULT_USER      : "/default-user-artwork.png",
    EXCEED_FILE_LIMIT   : "The uploaded image exceeds the file size limit of 10 MB.",
    IMAGE_SIZE_LIMIT    : 1000000, 
    acceptedMedia       : "image/x-png,image/gif,image/jpeg"
  },

  SongConstants :{
    SONG_SIZE_LIMIT : 10000000,
    EXCEED_FILE_LIMIT   : "The uploaded song exceeds the file size limit of 10 MB.",
  },

  LoginConstants :  {
    INVALID_EMAIL         : "Not a valid email address",
    REQUIRED_VALUE        : "You must enter a value",
    INVALID_CREDENTIALS   : "Incorrect email or password. Please try again",
  },

  NotificationConstants : {
    SUCCESS : "SUCCESS",
    ERROR   : "ERROR",
    WARN    : "WARNING"
  },

  AlbumConstants : {
    ADD_ALBUM_SUCCESS_MESSAGE_ADMIN   : "Successfully added the album.",
    ADD_ALBUM_SUCCESS_MESSAGE_USER    : "Successfully submitted request to add album" +
                                    "Please allow at most one business day for an admin to review and approve."
  },
  
};
// export let APP_CONFIG = new InjectionToken< BucciConstants >( 'app.config' );