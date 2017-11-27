// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production                : false,

  SERVER_PATH               : "http://localhost:8080",
  GET_ARTIST_ENDPOINT       : this.SERVER_PATH + "/artist",
  LOGIN_ENDPOINT            : this.SERVER_PATH + "/login",
  GET_ALBUM_ENDPOINT        : this.SERVER_PATH + "/album",
  ADD_ALBUM_ENDPOINT        : this.SERVER_PATH + "/approveAlbum",
  ADD_PLAYLIST_ENDPOINT     : this.SERVER_PATH + "/newplaylist",
  
  //LOGIN PAGE
  INVALID_EMAIL         : "Not a valid email address",
  REQUIRED_VALUE        : "You must enter a value",
  INVALID_CREDENTIALS   : "Incorrect email or password. Please try again",

  //ADD ALBUM
  SUCCESS_TITLE : "Success",
  ADD_ALBUM_SUCCESS_MESSAGE_ADMIN   : "Successfully added the album ",
  ADD_ALBUM_SUCCESS_MESSAGE_USER    : "Successfully submitted request to add album" +
                                      "Please allow at most one business day for an admin to review and approve.",
  // FILE CONSTANTS
  EXCEED_FILE_LIMIT   : "The uploaded image exceeds the file size limit of 10 MB.",
  IMAGE_SIZE_LIMIT    : 1000000, 
  acceptedMedia       : "image/x-png,image/gif,image/jpeg"
};
