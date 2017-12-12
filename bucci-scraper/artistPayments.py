import os
import json
from random import uniform, random
import MySQLdb
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
 
 
 


r1 = "2020-12-05 02:49:33"
r1 = "2020-12-05 02:49:33"
r1 = "2020-12-05 02:49:33"
r1 = "2020-12-05 02:49:33"
r1 = "2020-12-05 02:49:33"
r1 = "2020-12-05 02:49:33"

sql = "INSERT INTO buccidb2.concert (name, price, \
        purchase_link, release_date, location_id, artist_id) \
        VALUES ('" + name+ "'," + str(price) + ",'" + purchase_link + "\
       ' ,'" + str(release_date) + "',1," + str(artist_id) + ");" 



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
