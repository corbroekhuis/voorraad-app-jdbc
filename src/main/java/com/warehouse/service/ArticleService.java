package com.warehouse.service;

import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;

import java.util.Optional;

public interface ArticleService {
    Iterable<ArticleDTO> findAllDTOS();

    ArticleDTO saveDTO(ArticleDTO articleDTO);

    Optional<ArticleDTO> findDTOByEan(String ean);

    Iterable<Article> findAll();

    Article save(Article article);
}
