package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.ArtistMonthlyStat;
import com.buccitunes.model.ArtistMonthlyStatId;

public interface ArtistMonthlyStatRepository extends CrudRepository<ArtistMonthlyStat, ArtistMonthlyStatId>{

	@Query(value="CALL get_artist_last_month_stats();", nativeQuery = true)
	public List<ArtistMonthlyStat> getLastMonthStats();
	
	@Modifying
	@Query(value="CALL runStatBatch(0.07, '2017-12-1');", nativeQuery = true)
	public void updateStatsThisMonth();
	
	
	@Query(value="CALL get_artist_year_stats(:artistId);", nativeQuery = true)
	public List<ArtistMonthlyStat> getLastYearStats(@Param("artistId") int artistId);
	
}
