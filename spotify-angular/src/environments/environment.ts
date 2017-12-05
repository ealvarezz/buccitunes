// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production                : false,
  LOCAL_RESOURCE            :"/assets",
  SERVER_PATH               : "http://localhost:8080",
  GET_ARTIST_ENDPOINT       : "http://localhost:8080" + "/artist",
  GET_PLAYLIST_ENDPOINT     : "http://localhost:8080" + "/playlist",
  LOGIN_ENDPOINT            : "http://localhost:8080" + "/login",
  GET_ALBUM_ENDPOINT        : "http://localhost:8080" + "/album",
  ADD_ALBUM_ENDPOINT_ADMIN  : "http://localhost:8080" + "/add_album_admin",
  ADD_ALBUM_ENDPOINT_ARTIST : "http://localhost:8080" + "/request_album",
  ADD_PLAYLIST_ENDPOINT     : "http://localhost:8080" + "/new_playlist",
  SAVE_SONG_ENDPOINT        : "http://localhost:8080" + "/save_song",
  GET_SONG_LIBRARY_ENDPOINT : "http://localhost:8080" + "/saved_songs",
  SAVE_ALBUM_ENDPOINT       : "http://localhost:8080" + "/save_album",
  GET_SAVED_ALBUMS_ENDPOINT : "http://localhost:8080" + "/saved_albums",
  GET_REQ_ALBUMS_ENDPOINT   : "http://localhost:8080" + "/requested_albums",
  GET_REQ_ALBUM_ENDPOINT    : "http://localhost:8080" + "/requested_album_info",
  GET_REQ_SONGS_ENDPOINT    : "http://localhost:8080" + "/requested_album_info",
  APPROVE_ALBUM_ENDPOINT    : "http://localhost:8080" + "/approve_album",
  REJECT_ALBUM_ENDPOINT     : "http://localhost:8080" + "/disapprove_request_album",
  GET_RECENT_ALBUMS_ENDPOINT: "http://localhost:8080" + "/recently_played",
  GET_PLAYLIST_ENDPOINTS    : "http://localhost:8080" + "/user_playlists",
  UPDATE_PLAYLIST_ENDPOINT  : "http://localhost:8080" + "/change_playlist",
  FOLLOW_PLAYLIST_ENDPOINT  : "http://localhost:8080" + "/follow_playlist",
  DELETE_PLAYLIST_ENDPOINT  : "http://localhost:8080" + "/delete_playlist",
  GET_USER_ENDPOINT         : "http://localhost:8080" + "/user",
  FOLLOW_USER_ENDPOINT      : "http://localhost:8080" + "/follow",
  UNFOLLOW_USER_ENDPOINT    : "http://localhost:8080" + "/unfollow",


  ARTIST_ROLE               : "ARTIST",
  ADMIN_ROLE                : "ADMIN",
  PREMIUM_ROLE              : "PREMIUM",

  DEFAULT_ARTWORK           : "/default-cover-art.png",
  DEFAULT_ALBUM_ARTWORK     : "/default-album-artwork.png",
  DEFAULT_USER_ARTWORK      : "/default-user-artwork.png",

  //LOGIN PAGE
  INVALID_EMAIL         : "Not a valid email address",
  REQUIRED_VALUE        : "You must enter a value",
  INVALID_CREDENTIALS   : "Incorrect email or password. Please try again",

  //ADD ALBUM
  SUCCESS_TITLE : "Success",
  ADD_ALBUM_SUCCESS_MESSAGE_ADMIN   : "Successfully added the album.",
  ADD_ALBUM_SUCCESS_MESSAGE_USER    : "Successfully submitted request to add album" +
                                      "Please allow at most one business day for an admin to review and approve.",
  // FILE CONSTANTS
  EXCEED_FILE_LIMIT   : "The uploaded image exceeds the file size limit of 10 MB.",
  IMAGE_SIZE_LIMIT    : 1000000, 
  acceptedMedia       : "image/x-png,image/gif,image/jpeg"

};


