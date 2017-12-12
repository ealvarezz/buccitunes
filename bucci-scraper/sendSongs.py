from base64 import b64encode
from random_words import RandomWords
import requests
import os
import MySQLdb
import json
import sys
import binascii
 
s = requests.Session() 
rw = RandomWords()
 
PATH = "./bucci/"
ALBUM_ENDPOINT = "http://localhost:8080/request_album"
APPROVE_ALBUM_ENDPOINT = "http://localhost:8080/approve_album"
LOGIN_ENDPOINT = "http://localhost:8080/login"
LOGOUT_ENDPOINT = "http://localhost:8080/logout"
ROYALTY_ENDPOINT = "http://localhost:8080/pay_all_royalties"
 
albumPath = PATH+"dummy_album/"
albumFile = os.listdir(albumPath)[0]
album = json.loads(open(albumPath + albumFile, 'r').read())
album['songs'] = [] 
fname = PATH + "songs/"
admin = "bossman@gmail.com"

# Open database connection
db = MySQLdb.connect(host="127.0.0.1",  # Host of database 
                    user="root",        # User
                    passwd=sys.argv[1], # Password
                    port=3306,          # Port
                    db="buccidb2")      # db name
 
 
# prepare a cursor object using cursor() method
cursor = db.cursor()
 
get_all_songs_sql = 'SELECT * FROM buccidb2.song'

def update_song_name(audio_path, song_id):
    query = "UPDATE `buccidb2`.`song` SET `audio_path`='" + audio_path + "' WHERE `id`='" + str(song_id) + "';"
    try:
        # Execute the SQL command
        cursor.execute(query)
        # Commit your changes in the database
        db.commit()
    except:
        # Rollback in case there is any error
        db.rollback() 
    print(query)

def populate_songs():
    album['songs'] = []
    for i in os.listdir(fname):
        if(i[0] != '.'):
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

def populate_audio_in_db(songs):
    
    count = 0
    song_length = len(songs)
    
    # Execute the SQL command
    cursor.execute(get_all_songs_sql)
    # Fetch all the rows in a list of lists.
    results = cursor.fetchall()
    for row in results:
        update_song_name(songs[count % song_length]['audioPath'], row[0])
        count += 1

def send_request(the_chosen_one):
 
    login = {}
    login['email'] = the_chosen_one
    login['password'] = 'tmp'
    print(login)
    r = s.post(LOGIN_ENDPOINT, json=login)
    res = r.json()
    print(res)

    populate_songs()
    r = s.post(ALBUM_ENDPOINT, json=album)
    res = r.json()
    requested_album = res['response']
    
    # Now the user logs out
    s.post(LOGOUT_ENDPOINT)
   
    # Sign in as the admin to approve the album
    login['email'] = admin
    login['password'] = 'boss'
    r = s.post(LOGIN_ENDPOINT, json=login)
    res = r.json()
    print(res)
    
    for song in requested_album['songs']:
        song['approved'] = True

    r = s.post(APPROVE_ALBUM_ENDPOINT, json=requested_album)
    res = r.json()
    print(res)
    
    populate_audio_in_db(res['response']['songs'])

    r = s.post(ROYALTY_ENDPOINT)
    res = r.json()
    print(res)

send_request("1146@fake.com")

