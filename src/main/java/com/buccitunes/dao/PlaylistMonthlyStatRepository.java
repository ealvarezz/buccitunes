package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.PlaylistMonthlyStat;
import com.buccitunes.model.PlaylistMonthlyStatId;

public interface PlaylistMonthlyStatRepository extends CrudRepository<PlaylistMonthlyStat, PlaylistMonthlyStatId>{

}
