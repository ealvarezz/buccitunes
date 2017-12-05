import os
import json
from random import *
import MySQLdb
from random_words import RandomWords


rw = RandomWords()


# Open database connection
db = MySQLdb.connect(host="127.0.0.1",  # Host of database 
                    user="root",        # User
                    passwd="bucci",     # Password might want to change)
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
    return strTimeProp(start, end, '%m/%d/%Y %I:%M %p', prop)

def insert_concert_table(artist):

    name = rw.random_word()                 # Random date
    price = random.uniform(30.0, 100.0)     # Random price
    purchase_link = artist['name'] + ".com" # Artist website
    artist_id = artist['id']                # The artist id
    release_date = randomDate(datetime.datetime.now(), "1/1/2020 4:50 AM", random.random())                         # Random date generator

    sql = "INSERT INTO buccidb2.concert (name, artist_id, price, \
            purchase_link, release_date) VALUES (" + name + ", \
            " + artist_id + "," + str(price) + "," + purchase_link +"\
            ," + str(release_date) + ");" 
    try:
        # Execute the SQL command
        cursor.execute(sql)
        # Commit your changes in the database
        db.commit()
    except:
        # Rollback in case there is any error
        db.rollback()

def generate_concerts():

    # Execute the SQL command
    cursor.execute(random_artists_sql)
    # Fetch all the rows in a list of lists.
    results = cursor.fetchall()
    for row in results:
        artistId = row[0]
        insert_concert_table(artistId)


generate_concerts()
