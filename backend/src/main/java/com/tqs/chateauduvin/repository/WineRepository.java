package com.tqs.chateauduvin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.chateauduvin.model.Wine;

@Repository
public interface WineRepository extends JpaRepository<Wine,Long> {
    
}
