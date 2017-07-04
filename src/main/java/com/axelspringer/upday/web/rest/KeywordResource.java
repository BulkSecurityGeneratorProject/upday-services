package com.axelspringer.upday.web.rest;

import com.axelspringer.upday.domain.Article;
import com.axelspringer.upday.repository.ArticleRepository;
import com.axelspringer.upday.service.dto.ArticleDTO;
import com.axelspringer.upday.service.mapper.ArticleMapper;
import com.codahale.metrics.annotation.Timed;
import com.axelspringer.upday.domain.Keyword;

import com.axelspringer.upday.repository.KeywordRepository;
import com.axelspringer.upday.web.rest.util.HeaderUtil;
import com.axelspringer.upday.service.dto.KeywordDTO;
import com.axelspringer.upday.service.mapper.KeywordMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Keyword.
 */
@RestController
@RequestMapping("/api")
public class KeywordResource {

    private final Logger log = LoggerFactory.getLogger(KeywordResource.class);

    private static final String ENTITY_NAME = "keyword";

    private final KeywordRepository keywordRepository;

    private final KeywordMapper keywordMapper;

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public KeywordResource(KeywordRepository keywordRepository, KeywordMapper keywordMapper,
                           ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    /**
     * POST  /keywords : Create a new keyword.
     *
     * @param keywordDTO the keywordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new keywordDTO, or with status 400 (Bad Request) if the keyword has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/keywords")
    @Timed
    public ResponseEntity<KeywordDTO> createKeyword(@RequestBody KeywordDTO keywordDTO) throws URISyntaxException {
        log.debug("REST request to save Keyword : {}", keywordDTO);
        if (keywordDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new keyword cannot already have an ID")).body(null);
        }
        Keyword keyword = keywordMapper.toEntity(keywordDTO);
        keyword = keywordRepository.save(keyword);
        KeywordDTO result = keywordMapper.toDto(keyword);
        return ResponseEntity.created(new URI("/api/keywords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /keywords : Updates an existing keyword.
     *
     * @param keywordDTO the keywordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated keywordDTO,
     * or with status 400 (Bad Request) if the keywordDTO is not valid,
     * or with status 500 (Internal Server Error) if the keywordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/keywords")
    @Timed
    public ResponseEntity<KeywordDTO> updateKeyword(@RequestBody KeywordDTO keywordDTO) throws URISyntaxException {
        log.debug("REST request to update Keyword : {}", keywordDTO);
        if (keywordDTO.getId() == null) {
            return createKeyword(keywordDTO);
        }
        Keyword keyword = keywordMapper.toEntity(keywordDTO);
        keyword = keywordRepository.save(keyword);
        KeywordDTO result = keywordMapper.toDto(keyword);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, keywordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /keywords : get all the keywords.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of keywords in body
     */
    @GetMapping("/keywords")
    @Timed
    public List<KeywordDTO> getAllKeywords() {
        log.debug("REST request to get all Keywords");
        List<Keyword> keywords = keywordRepository.findAll();
        return keywordMapper.toDto(keywords);
    }

    /**
     * GET  /keywords/:id : get the "id" keyword.
     *
     * @param id the id of the keywordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the keywordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/keywords/{id}")
    @Timed
    public ResponseEntity<KeywordDTO> getKeyword(@PathVariable Long id) {
        log.debug("REST request to get Keyword : {}", id);
        Keyword keyword = keywordRepository.findOne(id);
        KeywordDTO keywordDTO = keywordMapper.toDto(keyword);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keywordDTO));
    }

    /**
     * DELETE  /keywords/:id : delete the "id" keyword.
     *
     * @param id the id of the keywordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/keywords/{id}")
    @Timed
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long id) {
        log.debug("REST request to delete Keyword : {}", id);
        keywordRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /keywords/:id : get the "id" keyword.
     *
     * @param description description wanted
     * @return the ResponseEntity with status 200 (OK) and with body the List<ArticleDTO>, or with status 404 (Not Found)
     */
    @GetMapping("/keywords/{description}/articles")
    @Timed
    public ResponseEntity<List<ArticleDTO>> getArticlesByKeyword(@PathVariable String description) {
        log.debug("REST request to get Keyword : {}", description);
        List<Article> articles = articleRepository.findByKeyword(description);
        List<ArticleDTO> articlesDTO = articleMapper.toDto(articles);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articlesDTO));
    }
}
