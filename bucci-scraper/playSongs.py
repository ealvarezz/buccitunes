import requests
import os
import json

#PATH = "./bucci/p"

PLAY_ENDPOINT = "http://localhost:8080/playsong"

users = ['1610@fake.com', '69@fake.com', '302@fake.com', '841@fake.com']
songsPlayed = [1, 4, 6, 9, 20, 100, 14, 11, 9, 12, 100, 400, 600, 900, 200, 100, 140, 110, 903, 120]
i = len(songsPlayed) - 1

for user in users:
    for played in songsPlayed:
        requestURL = PLAY_ENDPOINT + "?userId=" + user + "&songId=" + str(played)
        r = requests.get(requestURL)
        print(user + " has played " + r.json()['response']['name'])
    requestURL = PLAY_ENDPOINT + "?userId=" + user + "&songId=" + str(songsPlayed[i])
    r = requests.get(requestURL)
    print(user + " has played " + r.json()['response']['name'])
    i -= 1
