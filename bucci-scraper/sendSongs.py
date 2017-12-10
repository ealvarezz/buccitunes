from base64 import b64encode
from random_words import RandomWords
import requests
import os
import json
import sys
import binascii
 
s = requests.Session() 
rw = RandomWords()
 
PATH = "./bucci/"
ALBUM_ENDPOINT = "http://localhost:8080/request_album"
LOGIN_ENDPOINT = "http://localhost:8080/login"
 
albumPath = PATH+"dummy_album/"
albumFile = os.listdir(albumPath)[0]
album = json.loads(open(albumPath + albumFile, 'r').read())
album['songs'] = [] 
fname = PATH + "songs/"
admin = "bossman@gmail.com"

def populate_songs():
    album['songs'] = []
    for i in os.listdir(fname):
        bytes64 = b64encode(get_decoded_file(fname + i))
        base64_string = bytes64.decode('utf-8')
        song = {}
        song['audio'] = base64_string
        song['name'] = rw.random_word() + " " + rw.random_word()
        song['genres'] = []
        song['genres'].append({"id": 3})
        album['songs'].append(song)
        
def get_decoded_file(music_file):
    with open(music_file, 'rb') as f:
        data = f.read()
        return data

def send_request(the_chosen_one):
 
    login = {}
    login['email'] = the_chosen_one
    login['password'] = 'tmp'
    print(login)
    r = s.post(LOGIN_ENDPOINT, json=login)
    res = r.json()
    print(res)

    populate_songs();
    r = s.post(ALBUM_ENDPOINT, json=album)
    res = r.json()
    
    # Now the user logs out
    s.post(LOGOUT_ENDPOINT)
   
    # Sign in as the admin to approve the album
    login['email'] = admin
    login['password'] = 'boss'
    r = s.post(LOGIN_ENDPOINT, json=login)
    res = r.json()
    print(res)

    
    


send_request("1146@fake.com")

