package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.buccitunes.model.ArtistMonthlyStat;
import com.buccitunes.model.ArtistMonthlyStatId;

public interface ArtistMonthlyStatRepository extends CrudRepository<ArtistMonthlyStat, ArtistMonthlyStatId>{

	@Query(value="CALL get_artist_last_month_stats();", nativeQuery = true)
	public List<ArtistMonthlyStat> getLastMonthStats();
}
