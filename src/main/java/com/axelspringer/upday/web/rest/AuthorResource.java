package com.axelspringer.upday.web.rest;

import com.axelspringer.upday.domain.Article;
import com.axelspringer.upday.repository.ArticleRepository;
import com.axelspringer.upday.service.dto.ArticleDTO;
import com.axelspringer.upday.service.mapper.ArticleMapper;
import com.codahale.metrics.annotation.Timed;
import com.axelspringer.upday.domain.Author;

import com.axelspringer.upday.repository.AuthorRepository;
import com.axelspringer.upday.web.rest.util.HeaderUtil;
import com.axelspringer.upday.service.dto.AuthorDTO;
import com.axelspringer.upday.service.mapper.AuthorMapper;
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
 * REST controller for managing Author.
 */
@RestController
@RequestMapping("/api")
public class AuthorResource {

    private final Logger log = LoggerFactory.getLogger(AuthorResource.class);

    private static final String ENTITY_NAME = "author";

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public AuthorResource(AuthorRepository authorRepository, AuthorMapper authorMapper,
                          ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    /**
     * POST  /authors : Create a new author.
     *
     * @param authorDTO the authorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorDTO, or with status 400 (Bad Request) if the author has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authors")
    @Timed
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) throws URISyntaxException {
        log.debug("REST request to save Author : {}", authorDTO);
        if (authorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new author cannot already have an ID")).body(null);
        }
        Author author = authorMapper.toEntity(authorDTO);
        author = authorRepository.save(author);
        AuthorDTO result = authorMapper.toDto(author);
        return ResponseEntity.created(new URI("/api/authors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authors : Updates an existing author.
     *
     * @param authorDTO the authorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorDTO,
     * or with status 400 (Bad Request) if the authorDTO is not valid,
     * or with status 500 (Internal Server Error) if the authorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authors")
    @Timed
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody AuthorDTO authorDTO) throws URISyntaxException {
        log.debug("REST request to update Author : {}", authorDTO);
        if (authorDTO.getId() == null) {
            return createAuthor(authorDTO);
        }
        Author author = authorMapper.toEntity(authorDTO);
        author = authorRepository.save(author);
        AuthorDTO result = authorMapper.toDto(author);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authors : get all the authors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of authors in body
     */
    @GetMapping("/authors")
    @Timed
    public List<AuthorDTO> getAllAuthors() {
        log.debug("REST request to get all Authors");
        List<Author> authors = authorRepository.findAll();
        return authorMapper.toDto(authors);
    }

    /**
     * GET  /authors/:id : get the "id" author.
     *
     * @param id the id of the authorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/authors/{id}")
    @Timed
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
        log.debug("REST request to get Author : {}", id);
        Author author = authorRepository.findOne(id);
        AuthorDTO authorDTO = authorMapper.toDto(author);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authorDTO));
    }

    /**
     * DELETE  /authors/:id : delete the "id" author.
     *
     * @param id the id of the authorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authors/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        log.debug("REST request to delete Author : {}", id);
        authorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /authors/:id : get articles by the "id" author.
     *
     * @param id the id of the author
     * @return the ResponseEntity with status 200 (OK) and with body the List<Article>, or with status 404 (Not Found)
     */
    @GetMapping("/authors/{id}/articles")
    @Timed
    public ResponseEntity<List<ArticleDTO>> getArticlesByAuthor(@PathVariable Long id) {
        log.debug("REST request to get Author : {}", id);
        List<Article> articles = articleRepository.findByAuthor(id);
        List<ArticleDTO> articlesDTO = articleMapper.toDto(articles);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articlesDTO));
    }

}
