import requests
import os
import json
import sys

PATH = "./bucci/"
ARTISTPATH = PATH + "artist/"

ARTIST_ENDPOINT = "http://localhost:8080/add_artist_user"

cmdLength =  len(sys.argv)

if(cmdLength < 2):
    print("Need to specify artist")
    sys.exit()

file = ARTISTPATH + ' '.join(sys.argv[1:cmdLength]) + ".json"
artist_obj = json.loads(open(file, 'r').read())
r = requests.post(ARTIST_ENDPOINT, json=artist_obj)
print(file)
print(r.json())