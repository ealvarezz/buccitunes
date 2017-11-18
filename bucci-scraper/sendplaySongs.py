import requests
import os
import json

PLAYSONG_ENDPOINT = "http://localhost:8080/playsong?userId="


for y in range(30):
    requests.get(PLAYSONG_ENDPOINT + "ass@me.com" + "&songId=" + str(y))

for z in range(50):
    requests.get(PLAYSONG_ENDPOINT + "vagana@me.com" + "&songId=" + str(z))
for l in range(20, 30):
    requests.get(PLAYSONG_ENDPOINT + "vagana@me.com" + "&songId=" + str(l))
for v in range(1000):
    requests.get(PLAYSONG_ENDPOINT + "ass@me.com" + "&songId=" + str(v))


