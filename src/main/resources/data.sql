INSERT INTO `buccidb2`.`credit_company` (`id`, `name`) VALUES ('1', 'AMERICAN_EXPRESS')^;
INSERT INTO `buccidb2`.`credit_company` (`id`, `name`) VALUES ('2', 'DISCOVER')^;
INSERT INTO `buccidb2`.`credit_company` (`id`, `name`) VALUES ('3', 'MASTER_CARD')^;
INSERT INTO `buccidb2`.`credit_company` (`id`, `name`) VALUES ('4', 'VISA')^;

INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('1', 'HIPHOP')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('2', 'COUNTRY')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('3', 'POP')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('4', 'LATIN')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('5', 'SLAV')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('6', 'BLOCK')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('7', 'RNB')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('8', 'METAL')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('9', 'PROGRESSIVE_METAL')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('10', 'FUNK')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('11', 'ROCK')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('12', 'ELECTRONIC')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('13', 'JAZZ')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('14', 'PUNK')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('15', 'INDIE')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('16', 'RUSSIAN_OPERA')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('17', 'OPERA')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('18', 'WEEB')^;
INSERT INTO `buccidb2`.`genre` (`id`, `name`) VALUES ('19', 'UK_RAP')^;

INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('1', 'mp3')^;
INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('2', 'wav')^;
INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('3', 'midi')^;
INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('4', 'aif')^;
INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('5', 'm4a')^;
INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('6', 'jpeg')^;
INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('7', 'png')^;
INSERT INTO `buccidb2`.`mime_type` (`id`, `name`) VALUES ('8', 'jpg')^;



/*********************
* STORED PROCEDURES
*********************/

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_albums_by_tier_genre`(IN tier INT, IN genre_id INT, IN _limit INT)
BEGIN
SELECT albums_by_latest_date.* FROM
(
	SELECT AU.* FROM artist_user AU
	WHERE AU.tier = tier
	ORDER BY RAND()
	LIMIT _limit) AS selected_artists,
(
	SELECT AL2.*
	FROM album AL2
	INNER JOIN
	(
		SELECT AL.primary_artist_id, MAX(AL.release_date) AS latest_date
		FROM album AL
		GROUP BY AL.primary_artist_id) late_album 
	ON AL2.primary_artist_id = late_album.primary_artist_id
	WHERE  AL2.release_date = late_album.latest_date) albums_by_latest_date, 
buccidb2.genre_album GA
WHERE albums_by_latest_date.primary_artist_id = selected_artists.artist_id
AND albums_by_latest_date.id = GA.album_id AND GA.genre_id = genre_id;
END ^;


CREATE DEFINER=`root`@`localhost` PROCEDURE `topGenresForCurrentUser`(IN email VARCHAR(100))
BEGIN
	SELECT COUNT(GS.genre_id) AS genre_count, GS.genre_id 
	FROM genre_song GS, song_plays SP, user U
	WHERE U.email = email AND U.email = SP.user_id AND  SP.song_id = GS.song_id
	AND SP.date_played > DATE_SUB(NOW(), INTERVAL 1 WEEK)
	GROUP BY GS.genre_id
	ORDER BY(genre_count) DESC;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_recently_played`(IN email VARCHAR(100))
BEGIN
	SELECT DISTINCT M.artwork_path, M.date_created, M.title, A.* FROM
	(
		SELECT S.album_id, SP.date_played FROM 
		song S
		JOIN 
		song_plays SP
		ON S	.id = SP.song_id
		WHERE SP.user_id = email
		ORDER BY SP.date_played DESC
		LIMIT 30			
	) played_albums, album A, music_collection M
	WHERE A.id = played_albums.album_id AND A.id = M.id;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `search_playlist`(IN playlist_name VARCHAR(100))
BEGIN
	SELECT P.*, M.artwork_path, M.date_created, M.title 
	FROM playlist P, music_collection M
	WHERE M.title LIKE CONCAT('%', playlist_name, '%') AND M.id = P.id
	LIMIT 5;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `isFollowing`(
IN following VARCHAR(100),
IN followed VARCHAR(100),
OUT is_following bit(1))
BEGIN
	IF EXISTS(
		select U.email, f.followed_id
		from User U
		Join following f on f.following_id = following and f.followed_id = followed
		where U.email = following
		LIMIT 1
	) THEN
		SET is_following = 1;
    ELSE
		SET is_following = 0;
	END IF;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `search_album`(IN album_name VARCHAR(100))
BEGIN
	SELECT A.*, M.artwork_path, M.date_created, M.title 
	FROM album A, music_collection M
	WHERE M.title LIKE CONCAT('%', album_name, '%') AND M.id = A.id
	LIMIT 5;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `search_artist`(IN artist_name VARCHAR(100))
BEGIN
	SELECT * FROM artist A
	WHERE A.name LIKE CONCAT('%', artist_name, '%')
	LIMIT 5;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `search_song`(IN song_name VARCHAR(100))
	BEGIN
	SELECT * FROM song B
	WHERE B.name LIKE CONCAT('%', song_name, '%')
	LIMIT 5;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_needed_users_to_pay`()
BEGIN
	select u.*, pu.*, last_payed.date
	from user u
	join premium_user pu on pu.email = u.email
	join(
		select p.premium_user_id, max(p.date) as date
		from payment p
		group by p.premium_user_id
	) last_payed on last_payed.premium_user_id = u.email 
	and last_payed.date <=  DATE_SUB(NOW(), INTERVAL 30 DAY);
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `getConcertsOfArtistId`(IN artist_id int)
BEGIN 
	select c.*
	from artist a 
	join artists_concerts ac on ac.artist_id = a.id 
	join concert c on c.id = ac.concert_id and c.release_date >= curdate() 
	where a.id = artist_id
	UNION
	select c.*
	from artist a
	join concert c on c.artist_id = a.id and c.release_date >= curdate() 
	where a.id = artist_id;
END ^;


CREATE DEFINER=`root`@`localhost` PROCEDURE `runStatBatch`(IN in_revenue DECIMAL(10,5), IN  currDate date)
BEGIN
DECLARE currMonth INT;
DECLARE currYear INT;

SET currMonth = MONTH(currDate);
SET currYear = YEAR(currDate);


START TRANSACTION;


-- UPDATE SONG MONTHLY STATS
INSERT INTO song_monthly_stat
select DATE_FORMAT(currDate ,'%Y-%m-01') AS month, @curRow := @curRow + 1 AS rank , (in_revenue*p.total_plays) as revenue, p.total_plays,  p.song_id   from
(
select song_id, COUNT(*) as total_plays FROM song_plays
 WHERE MONTH(date_played) = currMonth
 AND YEAR(date_played) = currYear
 GROUP BY song_id
 ORDER BY total_plays DESC
 )p
 JOIN    (SELECT @curRow := 0) r;
 
 
 INSERT INTO album_monthly_stat
select DATE_FORMAT(currDate ,'%Y-%m-01') as month,  @curRow := @curRow + 1 AS rank, p.revenue, p.total_plays,  album_id
 FROM 
(
	SELECT album_id, sum(total_plays) as total_plays, sum(revenue) as revenue
    FROM
    (
		select  album_id,  s.total_plays as total_plays, s.revenue as revenue
		 from song_monthly_stat s
		 LEFT JOIN song
		ON s.song_id = song.id
        WHERE MONTH(s.month) = currMonth
        AND YEAR(s.month) = currYear
	) c
	GROUP BY album_id
    ORDER BY total_plays DESC
)p
 JOIN    (SELECT @curRow := 0) r;
 
 
 -- UPDATE ARTIST MONTHLY STATS
INSERT INTO artist_monthly_stat
SELECT DATE_FORMAT(currDate ,'%Y-%m-01') as month, @curRow := @curRow + 1 AS rank, p.revenue as revenue,   p.total_plays as total_plays, artist_id
FROM
(
	SELECT artist_id, sum(total_plays) as total_plays, sum(revenue) as revenue FROM
	(
		SELECT ms.*, a.primary_artist_id as artist_id FROM album_monthly_stat ms
		RIGHT JOIN album a
		ON ms.album_id = a.id
        WHERE MONTH(ms.month) = currMonth
        AND YEAR(ms.month) = currYear
	) k
	GROUP BY artist_id
    ORDER BY total_plays DESC
)p
 JOIN    (SELECT @curRow := 0) r;
 
 
 UPDATE stat_cache
        INNER JOIN
    (SELECT 
			stats_id,
            monthly_plays,
            monthly_revenue,
            total_plays,
            total_revenue,
            @curRow:=@curRow + 1 AS rank
    FROM
        (SELECT 
			stats_id,
            IFNULL(monthly_plays,0) as monthly_plays,
            IFNULL(monthly_revenue,0) as monthly_revenue,
            IFNULL(total_plays,0) as total_plays,
            IFNULL(total_revenue,0) as total_revenue,
            rank
    FROM
        song son
    LEFT JOIN (SELECT 
			song_id,
            AVG(total_plays) AS monthly_plays,
            AVG(revenue) AS monthly_revenue,
            0 AS rank,
            SUM(total_plays) AS total_plays,
            SUM(revenue) AS total_revenue
    FROM
        song_monthly_stat
    GROUP BY song_id) p ON son.id = p.song_id
    ORDER BY total_plays DESC) y
    JOIN (SELECT @curRow:=0) r) k ON stat_cache.id = k.stats_id 
SET 
    stat_cache.monthly_plays = k.monthly_plays,
    stat_cache.monthly_revenue = k.monthly_revenue,
    stat_cache.rank = k.rank,
    stat_cache.total_plays = k.total_plays,
    stat_cache.total_revenue = k.total_revenue;


 
 
 
 
 
 
 
 
UPDATE stat_cache
        INNER JOIN
    (SELECT 
        stats_id,
            monthly_plays,
            monthly_revenue,
            total_plays,
            total_revenue,
            @curRow:=@curRow + 1 AS rank
    FROM
        (SELECT 
        stats_id,
            monthly_plays,
            monthly_revenue,
            total_plays,
            total_revenue,
            rank
    FROM
        artist art
    LEFT JOIN (SELECT 
			artist_id,
            AVG(total_plays) AS monthly_plays,
            AVG(revenue) AS monthly_revenue,
            0 AS rank,
            SUM(total_plays) AS total_plays,
            SUM(revenue) AS total_revenue
    FROM
        artist_monthly_stat
    GROUP BY artist_id) p ON art.id = p.artist_id
    ORDER BY total_plays DESC) y
    JOIN (SELECT @curRow:=0) r) k ON stat_cache.id = k.stats_id 
SET 
    stat_cache.monthly_plays = k.monthly_plays,
    stat_cache.monthly_revenue = k.monthly_revenue,
    stat_cache.rank = k.rank,
    stat_cache.total_plays = k.total_plays,
    stat_cache.total_revenue = k.total_revenue;
    
    


UPDATE stat_cache
        INNER JOIN
    (SELECT 
        stats_id,
            monthly_plays,
            monthly_revenue,
            total_plays,
            total_revenue,
            @curRow:=@curRow + 1 AS rank
    FROM
        (SELECT 
        stats_id,
            monthly_plays,
            monthly_revenue,
            total_plays,
            total_revenue,
            rank
    FROM
        album alb
    LEFT JOIN (SELECT 
        album_id,
            AVG(total_plays) AS monthly_plays,
            AVG(revenue) AS monthly_revenue,
            0 AS rank,
            SUM(total_plays) AS total_plays,
            SUM(revenue) AS total_revenue
    FROM
        album_monthly_stat
    GROUP BY album_id) p ON alb.id = p.album_id
    ORDER BY total_plays DESC) y
    JOIN (SELECT @curRow:=0) r) k ON stat_cache.id = k.stats_id 
SET 
    stat_cache.monthly_plays = k.monthly_plays,
    stat_cache.monthly_revenue = k.monthly_revenue,
    stat_cache.rank = k.rank,
    stat_cache.total_plays = k.total_plays,
    stat_cache.total_revenue = k.total_revenue;

 

 
 COMMIT;
END^;