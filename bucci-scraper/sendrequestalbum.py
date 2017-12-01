import requests
import os
import json
import sys

ALBUMPATH = "./bucci/album/"


ALBUM_ENDPOINT = "http://localhost:8080/request_album"

cmdLength =  len(sys.argv)

if(cmdLength < 2):
    print("Need to specify album")
    sys.exit()

file = ALBUMPATH + ' '.join(sys.argv[1:cmdLength]) + ".json"
album_obj = json.loads(open(file, 'r').read())
r = requests.post(ALBUM_ENDPOINT, json=album_obj)
print(r.json())
