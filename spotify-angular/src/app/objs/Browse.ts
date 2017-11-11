export class BrowseList{
    header : String;
    isScrollable : boolean;
    itemsPerRow : number;
    rowHeight : number;
    items : BrowseItem[];


    constructor(header, isScrollable, itemsPerRow, items){
        this.header = header;
        this.isScrollable = isScrollable;
        this.itemsPerRow = itemsPerRow;
        this.items = items;
    }
}

export class BrowseItem{
    name: String;
    shortDescription : String;
    numFollowers : number;
    image : string;

    constructor(name: String, shortDescription: String, numFollowers : number, image : string){
        this.name = name;
        this.shortDescription = shortDescription;
        this.numFollowers = numFollowers;
        this.image = image;
    }
}