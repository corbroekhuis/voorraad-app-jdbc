package com.warehouse.service;

import com.warehouse.model.dto.ArticleDTO;

import java.util.Optional;

public interface ArticleService {
    Iterable<ArticleDTO> findAll();

    ArticleDTO save(ArticleDTO articleDTO);

    Optional<ArticleDTO> findByEan(String ean);
}
