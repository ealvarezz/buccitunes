MYSQL_PSSW=$1

python3 ./senddata.py
Rscript ./plays.r
python3 ./someUsers.py
python3 ./generate_concerts.py $MYSQL_PSSW
python3 ./generate_playlists.py $MYSQL_PSSW
