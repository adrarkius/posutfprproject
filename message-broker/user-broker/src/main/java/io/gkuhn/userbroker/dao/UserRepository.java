package io.gkuhn.userbroker.dao;


import io.gkuhn.userbroker.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUserid(int id);

}