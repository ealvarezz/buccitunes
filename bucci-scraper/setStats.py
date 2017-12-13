import MySQLdb
import sys

# Open database connection
db = MySQLdb.connect(host="127.0.0.1",  # Host of database 
                    user="root",        # User
                    passwd=sys.argv[1], # Password
                    port=3306,          # Port
                    db="buccidb2")      # db name
 
 
# prepare a cursor object using cursor() method
cursor = db.cursor()

query_start = "CALL runStatBatch(0.07, '2017-"
query_end = "-01');"

for i in range(1,12):
    
    month = ''

    if i < 10:
        month = "0" + str(i)
    else:
        month = str(i)
    try:
        # Execute the SQL command
        print(query_start + month + query_end)
        cursor.execute(query_start + month + query_end)
        # Commit your changes in the database
        db.commit()
    except:
        # Rollback in case there is any error
        db.rollback()
        print("Something went wrong")

# Close db connection
db.close()
