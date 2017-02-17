package com.duodian.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.duodian.domain.bean.User;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {
  public User findByEmail(String email);
} 
