package com.axelspringer.upday.repository;

import com.axelspringer.upday.domain.Article;
import com.axelspringer.upday.domain.Author;
import com.axelspringer.upday.domain.Keyword;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query("select distinct article from Article article left join fetch article.authors left join fetch article.keywords")
    List<Article> findAllWithEagerRelationships();

    @Query("select article from Article article left join fetch article.authors left join fetch article.keywords where article.id =:id")
    Article findOneWithEagerRelationships(@Param("id") Long id);

    @Query("SELECT article FROM Article article LEFT JOIN FETCH article.authors author WHERE author.id =:id")
    List<Article> findByAuthor(@Param("id") Long id);

    @Query("SELECT article FROM Article article LEFT JOIN FETCH article.keywords keyword WHERE keyword.description =:description")
    List<Article> findByKeyword(@Param("description") String description);

    List<Article> findByPublicationDateBetween(Instant startDate, Instant endDate);

}
