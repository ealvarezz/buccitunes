import requests
import os
import json

PATH = "./bucci/"

TIER_ENDPOINT = "http://localhost:8080/changetier?email="
count = 0

print("\n==============Assigning Tiers==============\n")
r = requests.get("http://localhost:8080/getallusers").json()
for i in r:
    print("processing " + i['email'])
    requests.get(TIER_ENDPOINT + i["email"] + "&tiercode=" + str(count % 4))
    count += 1

