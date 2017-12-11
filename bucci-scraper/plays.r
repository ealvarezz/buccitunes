args <- commandArgs(TRUE)
xx <- args[1]

installed <- require("RMySQL")
if(!installed){
  install.packages("RMySQL",repos = "https://cran.rstudio.com")
  library(RMySQL)
}
installed <- require("dplyr")
if(!installed){
  install.packages("dplyr", repos = "https://cran.rstudio.com")
  library(dplyr)
}

mydb = dbConnect(MySQL(), user='root', password=xx, dbname='buccidb2', host='localhost')

rs = dbSendQuery(mydb, "select * from song")
songs = fetch(rs, n=-1)

rs = dbSendQuery(mydb, "select  email from user LIMIT 50")
users = fetch(rs, n=-1)

rs = dbSendQuery(mydb, "select  * from song_plays")
song_plays = fetch(rs, n=-1)

max_range = 100

random = data.frame(replicate(1,sample(1:max_range,nrow(songs),rep=TRUE)))
colnames(random)[1] <-"num_rows"

songs$num_plays <- random$num_rows

songs.expanded <- songs[rep(seq_len(nrow(songs)), songs$num_plays), 1:2]


test <- songs.expanded %>% rowwise() %>% mutate(user= users[sample(nrow(users), 1), ] , date_played = sample(seq(as.Date('2017/01/01'), as.Date('2017/11/20'), by="day"), 1))

test <- test %>% select(song_id = id, user_id = user, date_played)


test$date_played <- format(test$date_played, "%Y-%m-%d")

test <- test[c("date_played", "song_id", "user_id")]

#setwd("/Users/sajidkamal/Desktop/r_script")
write.csv(test, file = "test.csv",row.names=FALSE)

query  <-  sprintf("LOAD DATA LOCAL INFILE '%s' 
INTO TABLE %s 
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
(date_played, song_id, user_id) SET id = NULL;" , './test.csv' , 'song_plays')

ts = dbSendQuery(mydb, query)
dbClearResult(ts)


dbDisconnect(mydb)

rs = dbSendQuery(mydb, "CALL runStatBatch(0.07, '2017-01-01'")


