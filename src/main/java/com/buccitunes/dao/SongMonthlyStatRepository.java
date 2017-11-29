package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;
import com.buccitunes.model.SongMonthlyStat;
import com.buccitunes.model.SongMonthlyStatId;

public interface SongMonthlyStatRepository extends CrudRepository<SongMonthlyStat, SongMonthlyStatId>{

}
