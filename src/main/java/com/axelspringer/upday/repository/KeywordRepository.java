package com.axelspringer.upday.repository;

import com.axelspringer.upday.domain.Keyword;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Keyword entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeywordRepository extends JpaRepository<Keyword,Long> {
    
}
