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





CREATE DEFINER=`root`@`localhost` PROCEDURE `get_albums_by_tier_genre`(IN tier INT, IN genre_id INT, IN _limit INT)
BEGIN
SELECT albums_by_latest_date.* FROM
(
	SELECT AU.* FROM buccidb2.artist_user AU
	WHERE AU.tier = tier
	ORDER BY RAND()
	LIMIT _limit) AS selected_artists,
(
	SELECT AL2.*
	FROM buccidb2.album AL2
	INNER JOIN
	(
		SELECT AL.primary_artist_id, MAX(AL.release_date) AS latest_date
		FROM buccidb2.album AL
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
	FROM buccidb2.genre_song GS, buccidb2.song_plays SP, buccidb2.user U
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
		buccidb2.song S
		JOIN 
		buccidb2.song_plays SP
		ON S	.id = SP.song_id
		WHERE SP.user_id = email
		ORDER BY SP.date_played DESC
		LIMIT 30			
	) played_albums, buccidb2.album A, buccidb2.music_collection M
	WHERE A.id = played_albums.album_id AND A.id = M.id;
END ^;

CREATE DEFINER=`root`@`localhost` PROCEDURE `isFollowing`(
IN following VARCHAR(100),
IN followed VARCHAR(100),
OUT is_following bit(1))
BEGIN
	IF EXISTS(
		select U.email, f.followed_id
		from User U
		Join following f on f.following_id = 'TismMan1@gmail.com' and f.followed_id = followed
		where U.email = following
		LIMIT 1
	) THEN
		SET is_following = 1;
    ELSE
		SET is_following = 0;
	END IF;
END




