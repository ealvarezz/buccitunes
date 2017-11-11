import { Component } from '@angular/core';
import {Song} from './objs/Song'
import {BrowseItem, BrowseList} from './objs/Browse';

@Component({
  selector: 'home-page',
  templateUrl: '../views/homepage.component.html'
})
export class HomePageComponent {

  private overView : BrowseList[];
  private recommended: BrowseList[];
  private genres : BrowseList[];
  private songs : Song[];


  ngOnInit(){


    this.overView = [
      (new BrowseList("Thirty Thursday's", true, 4, [
        new BrowseItem("Peaceful Piano","Relax and indulge with some profoundly beautiful piano pieces.", 3236869,"../assets/overview.png"),
        new BrowseItem("Deep Sleep","Drift into a deep sleep with these beautiful and ambient tracks.", 3236869,"../assets/overview.png"),
        new BrowseItem("Peaceful Guitar","Unwind to these calm acoustic guitar pieces", 3236869,"../assets/overview.png"),
        new BrowseItem("State of Jazz","Discover new releases across the diverse spectrum of jazz today.", 3236869,"../assets/overview.png")
      ]) ),(
      new BrowseList("Top Albums This Week", true, 4, [
        new BrowseItem("Beach House 3","Ty Dolla $ign", 0,"../assets/overview.png"),
        new BrowseItem("Motor Sport","Migos, Nicki Minaj, Cardi B", 0,"../assets/overview.png"),
        new BrowseItem("Wolves","Selena Gomez, Marshmello", 0,"../assets/overview.png"),
        new BrowseItem("Meaning Of Life","Kelly Clarkson.", 0,"../assets/overview.png")
      ]))
    ]


    this.recommended = [
      (new BrowseList("Recommended Albums", true, 4, [
        new BrowseItem("FUTURE","Future", 3236869, "../assets/overview.png"),
        new BrowseItem("Gay boi","Sayem Kamal", 3236869, "../assets/overview.png"),
        new BrowseItem("4 Your Eyez Only","J. Cole", 3236869, "../assets/overview.png"),
        new BrowseItem("Lifestyle","Rich Gang", 3236869, "../assets/overview.png")
      ]) ),(
      new BrowseList("Recommended Playlists", true, 4, [
        new BrowseItem("Discover Weekly","Your weekly mixtape of fresh music.", 0, "../assets/overview.png"),
        new BrowseItem("Channel X","Where RnB comes to play with Hip-Hop and Pop!", 0, "../assets/overview.png"),
        new BrowseItem("Rap Caviar","The best of Rap", 0, "../assets/overview.png"),
        new BrowseItem("Gold School","Classic Hip Hop.", 0, "../assets/overview.png")
      ]))
    ]

    this.genres = [
      new BrowseList("Recommended Albums", true, 4, [
        new BrowseItem(null,null, null, "../assets/hip_hop.png"),
        new BrowseItem(null,null, null, "../assets/pop.png"),
        new BrowseItem(null,null, null, "../assets/rnb.png"),
        new BrowseItem(null,null, null, "../assets/electro.png"),
        new BrowseItem(null,null, null, "../assets/rock.png"),
        new BrowseItem(null,null, null, "../assets/jazz.png"),
        new BrowseItem(null,null, null, "../assets/kpop.png"),
        new BrowseItem(null,null, null, "../assets/country.png")
      ])
    ]

    

    


  }
}
