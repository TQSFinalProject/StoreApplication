package com.tqs.chateauduvin.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tqs.chateauduvin.model.Wine;

@Repository
public interface WineRepository extends JpaRepository<Wine,Long> {
    Page<Wine> findByPriceBetweenAndAlcoholBetweenAndTypesContaining(Double minPrice, Double maxPrice, Double minAlc, Double maxAlc, String type, Pageable page);
    Page<Wine> findByPriceBetweenAndAlcoholBetween(Double minPrice, Double maxPrice, Double minAlc, Double maxAlc, Pageable page);
}
