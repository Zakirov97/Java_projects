package com.example.demo.repository;

import com.example.demo.model.Bitcoin;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface BitcoinRepo extends CrudRepository<Bitcoin, Long> {
}