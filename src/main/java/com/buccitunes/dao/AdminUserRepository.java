package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.AdminUser;

@Transactional
public interface AdminUserRepository extends BaseUserRepository<AdminUser>, CrudRepository<AdminUser, String> {

}
