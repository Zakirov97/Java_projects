package com.example.demo.repository;

import com.example.demo.model.Covid19AffectedCountries;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Covid19AffCounRepo extends CrudRepository<Covid19AffectedCountries, Long> {
}