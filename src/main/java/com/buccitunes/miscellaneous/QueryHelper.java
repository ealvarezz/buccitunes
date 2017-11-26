package com.buccitunes.miscellaneous;

public class QueryHelper {
		
	public static final String TIME_PARAM = "timeAgo";
	
	public static final String SELECT_PLAY_COUNTS = "sum(sp_count.song_count) as " + BucciConstants.PLAY_COUNT + " ";
	
	public static final String PLAY_COUNTS_BY_TIME_QUERY = ""
			+ "join( "
			+ "	select sp.song_id, count(sp.song_id) as song_count "
			+ "	from song_plays sp "
			+ "	where sp.date_played <= NOW() and sp.date_played >= (CURDATE() - :" + TIME_PARAM + " ) "
			+ "	group by sp.song_id "
			+ ") sp_count on sp_count.song_id = s.id ";
}
