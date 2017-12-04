export class MediaFile{
    constructor(artwork : string, artworkPath : string){
        this.artwork = artwork;
        this.artworkPath = artworkPath;
    }
    artwork : string;
    artworkPath : string;
    error : boolean;
    errorMsg : string;

}