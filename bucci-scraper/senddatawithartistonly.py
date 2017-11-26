import requests
import os
import json

PATH = "./bucci/"

ARTIST_ENDPOINT = "http://localhost:8080/add_artist_user"

#try:
print("\n==============Creating Artists==============\n")
artpath = PATH+"artist/"
for i in os.listdir(artpath):
    print(artpath+i)
    artist_obj = json.loads(open(artpath+i, 'r').read())
    r = requests.post(ARTIST_ENDPOINT, json=artist_obj)