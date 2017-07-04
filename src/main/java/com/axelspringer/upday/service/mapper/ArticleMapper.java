package com.axelspringer.upday.service.mapper;

import com.axelspringer.upday.domain.*;
import com.axelspringer.upday.service.dto.ArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Article and its DTO ArticleDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class, KeywordMapper.class, })
public interface ArticleMapper extends EntityMapper <ArticleDTO, Article> {
    
    
    default Article fromId(Long id) {
        if (id == null) {
            return null;
        }
        Article article = new Article();
        article.setId(id);
        return article;
    }
}
