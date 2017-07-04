package com.axelspringer.upday.service.mapper;

import com.axelspringer.upday.domain.*;
import com.axelspringer.upday.service.dto.AuthorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Author and its DTO AuthorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorMapper extends EntityMapper <AuthorDTO, Author> {
    
    
    default Author fromId(Long id) {
        if (id == null) {
            return null;
        }
        Author author = new Author();
        author.setId(id);
        return author;
    }
}
