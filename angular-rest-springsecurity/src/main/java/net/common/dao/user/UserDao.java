package net.common.dao.user;

import net.common.dao.Dao;
import net.common.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService{

	User findByName(String name);
}