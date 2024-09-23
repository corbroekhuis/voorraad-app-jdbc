package com.warehouse.service;

import com.warehouse.component.ArticleMapper;
import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    ArticleRepository articleRepository;
    ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper){
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public Optional<ArticleDTO> findById(Long id) {

        Optional<Article> found = articleRepository.findById( id);

        if( found.isEmpty()){
            return Optional.empty();
        }

        ArticleDTO articleDTO = articleMapper.toDto( found.get());
        return Optional.of(articleDTO);
    }

    @Override
    public Iterable<ArticleDTO> findAll() {

        List<ArticleDTO> articlesDTO = new ArrayList<>();
        for( Article article: articleRepository.findAll()){
            articlesDTO.add( articleMapper.toDto( article));
        }

        return articlesDTO;
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById( id);
    }

    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {

        Article article = articleMapper.toArticle( articleDTO);
        article = articleRepository.save( article);

        return articleMapper.toDto( article);
    }
}
