package com.warehouse.service;

import com.warehouse.model.dto.ArticleDTO;

import java.util.Optional;

public interface ArticleService {

    Optional<ArticleDTO> findById(Long id);

    Iterable<ArticleDTO> findAll();

    void deleteById(Long id);

    ArticleDTO save(ArticleDTO article);
}
