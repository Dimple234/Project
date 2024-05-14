package com.cloth.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloth.entities.product;


public interface itemDto extends JpaRepository<product, Integer> {

}
