package com.warehouse.service;

import com.warehouse.model.dto.ArticleDTO;

public interface ArticleService {
    Iterable<ArticleDTO> findAll();

    ArticleDTO save(ArticleDTO articleDTO);
}
