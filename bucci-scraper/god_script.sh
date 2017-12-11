MYSQL_PSSW=$1

python3 ./senddata.py
python3 ./someUsers.py
Rscript ./plays.r $MYSQL_PSSW
python3 ./generate_concerts.py $MYSQL_PSSW
python3 ./generate_playlists.py $MYSQL_PSSW
python3 ./setStats.py $MYSQL_PSSW
