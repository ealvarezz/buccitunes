import sys
import os
import json
import re
import base64
from pylyrics_fixed import *
import wikipedia


file_extension_reg = re.compile(".*\.(\w+)$")
release_reg = re.compile("eleased.*\s(\w+)\s(\d+),\s(\d+)")
release_euro = re.compile("eleased.*\s(\d+)\s(\w+)\s(\d+)")
NUM_ALBUMS = 3
songcounter = 0
Months = {
        "January": "01",
        "February": "02",
        "March": "03",
        "April": "04",
        "May": "05",
        "June": "06",
        "July": "07",
        "August": "08",
        "September": "09",
        "October": "10",
        "November": "11",
        "December": "12",
        }
Genres = {
        "HIPHOP": 1,
        "COUNTRY": 2,
        "POP": 3,
        "LATIN": 4,
        "SLAV": 5,
        "BLOCK": 6,
        "RNB": 7,
        "METAL": 8,
        "PROGRESSIVE_METAL": 9,
        "FUNK": 10,
        "ROCK": 11,
        "ELECTRONIC": 12,
        "JAZZ": 13,
        "PUNK": 14,
        "INDIE": 15,
        "RUSSIAN_OPERA": 16,
        "OPERA": 17,
        "WEEB": 18,
        "UK_RAP": 19
        }

def create_data(artist_list):
    if not os.path.exists('./bucci'):
        os.makedirs('./bucci')
    if not os.path.exists('./bucci/artist'):
        os.makedirs('./bucci/artist')
    if not os.path.exists('./bucci/album'):
        os.makedirs('./bucci/album')

    for i in artist_list:
        get_albums((i[0],i[1],i[2]))


def get_albums(artist):
    global songcounter
    try:
        print(artist)

        albums = PyLyrics.getAlbums(singer=artist[0])[0:NUM_ALBUMS]
        page = get_wikipage(str(artist[0]), " musician")

        artistdict = artist[1]
        artistdict['biography'] = page[1]
        artistdict['avatar'] = page[0]

        for album in albums:
            try:
                print("\t"+str(album))
                tracks = album.tracks()
                albumdict = {}
                albumdict['title'] = str(album)
                albumdict['primaryArtist'] = {'name': str(artist[0])}
                tracks = []
                k = album.tracks()

                for track in album.tracks():
                    trackdict = {}
                    trackdict['genres'] = [{"id": artist[2]}]
                    trackdict['isExplicit'] = False
                    trackdict['name'] = unicode(track).encode("utf8")
                    try:
                        trackdict['lyrics'] = {"lyric": unicode(track.getLyrics()).encode("utf8")}
                        if trackdict['lyrics']['lyric'].startswith('<span'):
                            trackdict['lyrics'] = None
                    except Exception as e:
                        print(e)
                        print("Problem with track, skipping " + unicode(track).encode("utf8"))

                    tracks.append(trackdict)
                    songcounter = songcounter + 1

                albumdict['songs'] = tracks
                ree = get_wikipage(str(album), " album", date=True)
                albumdict['releaseDate'] = ree[1]
                albumdict['artwork'] = ree[0]
                albumdict['genres'] = [{"id": artist[2]}]

                f = open('./bucci/album/'+str(album)+'.json', 'w+')
                f.write(json.dumps(albumdict, indent=4))
                f.close()
            except Exception as e:
                print e
                print "Problem in album " + str(album)
                print "========================================"
                continue
        
        f = open('./bucci/artist/'+str(artist[0])+'.json', 'w+')
        f.write(json.dumps({'artist': artistdict, 'email': str(songcounter)+"@fake.com"}, indent=4))
        f.close()
        print "========================================"
    except Exception as e:
        print e
        print "could not find albums for: " + artist
        print "========================================"


def get_info(artist):
    try:
        k = wikipedia.summary(artist)
        return k
    except Exception as e:
        artist += " musician"
        print(artist)
        k = wikipedia.summary(artist)
        return k

#YYYY-MM-DD released on July 3, 2001
def get_wikipage(name, type_of_page, date=False):
    global release_reg
    global Months
    global songcounter

    try:
        k = wikipedia.page(name + type_of_page)
    except:
        return (None,None)


    image = None
    if k.images:
        for image_path in k.images:
            if not image_path.endswith("svg") and not image_path.endswith("ogg"):
                image = base64.b64encode(requests.get(image_path).content)
                break

    ans = None

    if date:
        date = release_reg.search(k.summary)
        if date:
            day = date.groups()[1] if len(date.groups()[1]) > 1 else "0"+date.groups()[1] 
            ans = date.groups()[2]+"-"+Months[date.groups()[0]]+"-"+day
        else:
            date = release_euro.search(k.summary)
            if date:
                day = date.groups()[0] if len(date.groups()[0]) > 1 else "0"+date.groups()[0] 
                ans = date.groups()[2]+"-"+Months[date.groups()[1]]+"-"+day
    else:
        ans = k.summary

    return (image, ans)


    

# PROGRAM ARTIST SONG
if __name__ == "__main__":
    f = open(os.devnull, 'w')
    sys.stderr = f

    artist_list = sys.stdin.read().split("\n")[:-1]

    #TODO FIX GENRE ID
    artist_list = [(i.split(",")[0], {"name": i.split(",")[0]}, Genres[i.split(",")[1]]) for i in artist_list]

    try:
        create_data(artist_list)
        print songcounter
    except Exception as e:
        print(e)
