package com.axelspringer.upday.service.mapper;

import com.axelspringer.upday.domain.*;
import com.axelspringer.upday.service.dto.KeywordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Keyword and its DTO KeywordDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KeywordMapper extends EntityMapper <KeywordDTO, Keyword> {
    
    
    default Keyword fromId(Long id) {
        if (id == null) {
            return null;
        }
        Keyword keyword = new Keyword();
        keyword.setId(id);
        return keyword;
    }
}
