package com.axelspringer.upday.repository;

import com.axelspringer.upday.domain.Article;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

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

    @Query("select distinct article from Article article left join fetch article.authors left join fetch article.keywords where article.publicationDate between :startDate and :endDate")
    List<Article> findByPublicationDateBetween(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

}
