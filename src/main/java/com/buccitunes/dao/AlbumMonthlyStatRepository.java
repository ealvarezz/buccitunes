package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.AlbumMonthlyStat;
import com.buccitunes.model.AlbumMonthlyStatId;

public interface AlbumMonthlyStatRepository extends CrudRepository<AlbumMonthlyStat, AlbumMonthlyStatId>{

}
