import requests
import os
import json

PATH = "./bucci/"

ARTIST_ENDPOINT = "http://localhost/addArtist"
ALBUM_ENDPOINT = "http://localhost/approveAlbum"


#try:
artpath = PATH+"artist/"
for i in os.listdir(artpath):
    print(artpath+i)
    artist_obj = json.loads(open(artpath+i, 'r').read())
    r = requests.post(ARTIST_ENDPOINT, json=artist_obj)
#except:
#    print("shit")

#try:
albpath = PATH+"album/"
for i in os.listdir(albpath):
    print(artpath+i)
    artist_obj = json.loads(open(albpath+i, 'r').read())
    r = requests.post(ALBUM_ENDPOINT, json=artist_obj)
#except:
#    print("shit")
