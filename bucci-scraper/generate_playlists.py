import requests
import os
import json
from random import *
import MySQLdb
from random_words import RandomWords
import sys
 
PATH = "./bucci/"
PLAYLIST_ENDPOINT = "http://localhost:8080/new_playlist"
LOGIN_ENDPOINT = "http://localhost:8080/login"
LOGOUT_ENDPOINT = "http://localhost:8080/logout"
 
s = requests.Session()
rw = RandomWords()
 
# Open database connection
db = MySQLdb.connect(host="127.0.0.1",  # Host of database 
                    user="root",        # User
                    passwd=sys.argv[1], # Password
                    port=3306,          # Port
                    db="buccidb2")      # db name
 
 
# prepare a cursor object using cursor() method
cursor = db.cursor()
 
def insert_into_playlist_song_table(songId, playlistId):
 
    sql = "INSERT INTO buccidb2.playlist_song(playlist_id,song_id) \
            VALUES (" + str(playlistId) + "," + str(songId) + ")"
    try:
        # Execute the SQL command
        cursor.execute(sql)
        # Commit your changes in the database
        db.commit()
    except:
        # Rollback in case there is any error
        db.rollback() 
 
def add_random_songs(playlist):
    random_songs_sql = 'SELECT * FROM buccidb2.song \
                        ORDER BY RAND() \
                        LIMIT 10;'
    # Execute the SQL command
    cursor.execute(random_songs_sql)
    # Fetch all the rows in a list of lists.
    results = cursor.fetchall()
    for row in results:
        songId = row[0]
        insert_into_playlist_song_table(songId, playlist['id'])
 
 
def add_playlists():
    
    userPath = PATH+"users/"
    usersFile = os.listdir(userPath)[0]
    print(PATH+usersFile)
    for i in range(1,12):
        users = json.loads(open(userPath + usersFile, 'r').read())
        for user in users:
            signUp = user
            emailSplit = signUp['userInfo']['email'].split('@');
            email = emailSplit[0] + str(i) + '@' + emailSplit[1]
            print(email)    
            # User first logs in
            login = {}
            login['email'] = email
            login['password'] = 'tmp'
            print(login)
            r = s.post(LOGIN_ENDPOINT, json=login)
            res = r.json()
            print(res)
            user = res['response']
            
 
            # User creates the playlist
            playlist = {}
            playlist['owner'] = { "email": user['email']}
            playlist['title'] = rw.random_word() + " " + rw.random_word()
            print(playlist)
            r = s.post(PLAYLIST_ENDPOINT, json=playlist)
            res = r.json()
            print(res)
            playlist = res['response']
            print(playlist)
 
            # We adds songs to the playlist
            add_random_songs(playlist)
 
            # Now the user logs out
            s.post(LOGOUT_ENDPOINT)
 
add_playlists()
db.close()
