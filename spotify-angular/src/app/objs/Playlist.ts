import {Song} from './Song';
import {User} from './User';

export class Playlist{
   public: boolean;
   collaborative : boolean;
   id : number;
   title : string;
   songs : Song[];
   dateCreated : Date;
   owner : User;
   collaboratives : User[];
   followers : User[];
   artwork : string;
   stats : any;

}