import {Artist} from './Artist';

export class Song{
    public name: string = "Homecoming";
    public owner: Artist;
    public rating : number;
    public featuredArtists: Artist[];
    public mimeType : string;
    public genres : string[];
    public lyrics : string;
    public album_image : string = "/assets/graduation.png";
    public song_file : string = "/assets/rockstar.mp3";
    public duration : String = "3:30";
    public album : String = "Graduation"

}



//    {
//                 "name": "Beware",
//                 "duration": 0,
//                 "rating": 0,
//                 "isExplicit": false,
//                 "owner": {
//                     "id": 12,
//                     "name": "Death Grips",
//                     "biography": "The Powers That B is the fourth studio album, and first double album, by experimental hip hop group Death Grips. The album's first disc, Niggas on the Moon, was released as a free digital download on June 8, 2014. The first disc's instrumentation was performed entirely on a Roland V-Drum kit by drummer Zach Hill and features chopped vocal samples of Icelandic singer-songwriter Björk on every track. The second half, Jenny Death, leaked online on March 19, 2015. It features Nick Reinhart of Tera Melos, and Zach's former high school bandmate, Julian Imsdahl, on guitar and organ respectively. The double album as a whole was officially released via Harvest Records on March 31, 2015.\n\n",
//                     "avatar": "/Users/sajidkamal/BucciTunesFiles/ARTISTS/12/artwork.png",
//                     "albums": null,
//                     "followers": null,
//                     "features": null,
//                     "upcomingConcerts": null,
//                     "featuredAlbum": null,
//                     "recentlyPlayed": null,
//                     "stats": {
//                         "totalPlays": 0,
//                         "monthlyPlays": 0,
//                         "totalRevenue": 0,
//                         "monthlyRevenue": 0,
//                         "popularity": 0,
//                         "monthlyStats": 0
//                     }
//                 },
//                 "featuredArtists": null,
//                 "mimeType": null,
//                 "genres": null,
//                 "picturePath": null,
//                 "audioPath": null,
//                 "stats": {
//                     "totalPlays": 0,
//                     "monthlyPlays": 0,
//                     "totalRevenue": 0,
//                     "monthlyRevenue": 0,
//                     "popularity": 0,
//                     "monthlyStats": 0
//                 },
//                 "lyrics": null,
//                 "audioAsFile": "/Users/sajidkamal/Desktop/buccitunes"
//             },