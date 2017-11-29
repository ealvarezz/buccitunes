# | python {THIS FILE}
import requests
import os
import json
import time

AUDIO_ENDPOINT = "http://localhost:8080/add_audio"
SONGPATH = "./bucci/audio/song.json"
ENCODEDSTR = "./bucci/audio/encoded"


print("\n==============Creating Audio==============\n")
song_obj = json.loads(open(SONGPATH, 'r').read())
r = requests.post(AUDIO_ENDPOINT, json=song_obj)
print(r.json())




