package com.foodeo.backend.repository;


import com.foodeo.backend.model.SearchEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchEngineRepository extends JpaRepository<SearchEngine, String> {

}
