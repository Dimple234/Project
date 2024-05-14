package com.cloth.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cloth.entities.User;

@Repository
@Component
public interface UserDto extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.email =:email ")
	public User getUserByUserName(@org.springframework.data.repository.query.Param("email") String email)	;
	 
	
}
