package com.axelspringer.upday.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.axelspringer.upday.domain.Article;

import com.axelspringer.upday.repository.ArticleRepository;
import com.axelspringer.upday.web.rest.util.HeaderUtil;
import com.axelspringer.upday.service.dto.ArticleDTO;
import com.axelspringer.upday.service.mapper.ArticleMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Article.
 */
@RestController
@RequestMapping("/api")
public class ArticleResource {

    private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

    private static final String ENTITY_NAME = "article";

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleResource(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    /**
     * POST  /articles : Create a new article.
     *
     * @param articleDTO the articleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articleDTO, or with status 400 (Bad Request) if the article has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/articles")
    @Timed
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) throws URISyntaxException {
        log.debug("REST request to save Article : {}", articleDTO);
        if (articleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new article cannot already have an ID")).body(null);
        }
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        ArticleDTO result = articleMapper.toDto(article);
        return ResponseEntity.created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /articles : Updates an existing article.
     *
     * @param articleDTO the articleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articleDTO,
     * or with status 400 (Bad Request) if the articleDTO is not valid,
     * or with status 500 (Internal Server Error) if the articleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/articles")
    @Timed
    public ResponseEntity<ArticleDTO> updateArticle(@RequestBody ArticleDTO articleDTO) throws URISyntaxException {
        log.debug("REST request to update Article : {}", articleDTO);
        if (articleDTO.getId() == null) {
            return createArticle(articleDTO);
        }
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        ArticleDTO result = articleMapper.toDto(article);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /articles : get articles.
     * @param startDate lower bound to filter by date (optional)
     * @param endDate upper bound to filter by date (optional)
     * @return the ResponseEntity with status 200 (OK) and the list of articles in body
     */
    @GetMapping("/articles")
    @Timed
    public List<ArticleDTO> getArticles(@RequestParam(required = false) ZonedDateTime startDate,
                                        @RequestParam(required = false) ZonedDateTime endDate) {
        log.debug("REST request to get all Articles");
        List<Article> articles;
        if (startDate == null && endDate == null) {
            articles = articleRepository.findAllWithEagerRelationships();
        } else {
            final ZonedDateTime validStartDate = startDate == null ? ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()) : startDate;
            final ZonedDateTime validEndDate = endDate == null ? ZonedDateTime.now() : endDate;
            articles = articleRepository.findByPublicationDateBetween(validStartDate, validEndDate);
        }
        return articleMapper.toDto(articles);
    }

    /**
     * GET  /articles/:id : get the "id" article.
     *
     * @param id the id of the articleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/articles/{id}")
    @Timed
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long id) {
        log.debug("REST request to get Article : {}", id);
        Article article = articleRepository.findOneWithEagerRelationships(id);
        ArticleDTO articleDTO = articleMapper.toDto(article);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articleDTO));
    }

    /**
     * DELETE  /articles/:id : delete the "id" article.
     *
     * @param id the id of the articleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/articles/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        log.debug("REST request to delete Article : {}", id);
        articleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
