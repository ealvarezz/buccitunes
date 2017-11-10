package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.MimeType;

public interface MimeTypeRepository extends CrudRepository<MimeType, Integer> {
	
}
