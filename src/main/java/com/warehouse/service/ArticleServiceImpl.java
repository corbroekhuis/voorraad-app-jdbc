package com.warehouse.service;

import com.warehouse.mapper.ArticleMapper;
import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    ArticleRepository articleRepository;
    ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public Iterable<ArticleDTO> findAll() {

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        Iterable<Article> articles = articleRepository.findAll();

        for( Article article: articles){
            articleDTOS.add( articleMapper.toArticleDTO( article));
        }

        return articleDTOS;
    }

    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {

        Article article = articleMapper.toArticle( articleDTO);
        article = articleRepository.save( article);

        return articleMapper.toArticleDTO( article);
    }
}
