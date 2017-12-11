package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.ActivityFeed;
import com.buccitunes.model.ActivityFeedId;

public interface ActivityFeedRepository extends CrudRepository<ActivityFeed, ActivityFeedId>{

	@Query(value="CALL get_following_activity(:email);", nativeQuery = true)
	public List<ActivityFeed> getUserFeed(@Param("email") String email);
}
