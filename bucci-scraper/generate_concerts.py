import os
import json
from random import uniform, random
import MySQLdb
from random_words import RandomWords
import datetime
import time
import sys

rw = RandomWords()
 
 
# Open database connection
db = MySQLdb.connect(host="127.0.0.1",  # Host of database 
                    user="root",        # User
                    passwd=sys.argv[1], # Password
                    port=3306,          # Port
                    db="buccidb2")      # db name
 
 
# prepare a cursor object using cursor() method
cursor = db.cursor()
 
random_artists_sql = 'SELECT * FROM buccidb2.artist \
                      ORDER BY RAND() \
                      LIMIT 20;'
 
def strTimeProp(start, end, format, prop):
    """Get a time at a proportion of a range of two formatted times.
 
    start and end should be strings specifying times formated in the
    given format (strftime-style), giving an interval [start, end].
    prop specifies how a proportion of the interval to be taken after
    start.  The returned time will be in the specified format.
    """
 
    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))
 
    ptime = stime + prop * (etime - stime)
 
    return time.strftime(format, time.localtime(ptime))
 
 
def randomDate(start, end, prop):
    return strTimeProp(start, end, '%Y-%m-%d %H:%M:%S', prop)
 
def insert_concert_table(artistId, artistName):
    name = rw.random_word() + " " + rw.random_word() # Random date
    price = uniform(30.0, 100.0)                     # Random price
    purchase_link = artistName + ".com"              # Artist website
    artist_id = artistId                             # The artist id
    release_date = randomDate(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S'), "2020-12-05 02:49:33", random()) # Random date generator
    
    sql = "INSERT INTO buccidb2.concert (name, price, \
            purchase_link, release_date, location_id, artist_id) \
            VALUES ('" + name+ "'," + str(price) + ",'" + purchase_link + "\
           ' ,'" + str(release_date) + "',1," + str(artist_id) + ");" 
    
    print(sql)
    try:
        # Execute the SQL command
        cursor.execute(sql)
        # Commit your changes in the database
        db.commit()
    except:
        # Rollback in case there is any error
        db.rollback()
        print("Something went wrong")
 
def generate_concerts():
 
    # Execute the SQL command
    cursor.execute(random_artists_sql)
    # Fetch all the rows in a list of lists.
    results = cursor.fetchall()
    for row in results:
        insert_concert_table(row[0], row[3])
 
for i in range(0,10):
    generate_concerts()
