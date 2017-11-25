import requests
import os
import json

PATH = "./bucci/"

ARTIST_ENDPOINT = "http://localhost:8080/changetier?email="


print("\n==============Assigning Tiers==============\n")
r = requests.get("http://localhost:8080/getallusers");
for i in r:
    print(i["email"])
   # artist_obj = json.loads(open(artpath+i, 'r').read())
   # requests.get(ARTIST_ENDPOINT + artist_obj["email"] + "&tier=" + str(i % 4))

