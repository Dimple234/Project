package com.cloth.Dao;

import org.springframework.data.jpa.repository.JpaRepository;



public interface orderDao extends JpaRepository<com.cloth.entities.OrderAdmin, Integer> {

}
