package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;
import com.buccitunes.model.ArtistMonthlyStat;
import com.buccitunes.model.ArtistMonthlyStatId;

public interface ArtistMonthlyStatRepository extends CrudRepository<ArtistMonthlyStat, ArtistMonthlyStatId>{

}
