package com.warehouse.service;

import com.warehouse.mapper.ArticleMapper;
import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Iterable<ArticleDTO> findAllDTOS() {

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        Iterable<Article> articles = articleRepository.findAll();

        for( Article article: articles){
            articleDTOS.add( articleMapper.toArticleDTO( article));
        }

        return articleDTOS;
    }

    @Override
    public ArticleDTO saveDTO(ArticleDTO articleDTO) {

        Article article = articleMapper.toArticle( articleDTO);
        article = articleRepository.save( article);

        return articleMapper.toArticleDTO( article);
    }

    @Override
    public Optional<ArticleDTO> findDTOByEan(String ean) {
        Optional<Article> article = articleRepository.findByEan( ean);

        if( article.isPresent()){
            ArticleDTO articleDTO = articleMapper.toArticleDTO(article.get());
            return Optional.of( articleDTO);
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save( article);
    }

    @Override
    public void deleteByEan(String ean){

        articleRepository.deleteByEan( ean);
    }

    @Override
    public void updateStock(String ean, int amount) throws Exception {
        Optional<Article> optional = articleRepository.findByEan( ean);

        if(optional.isEmpty()){
            throw new Exception( "Artikel niet gevonden");
        }

        Article article = optional.get();

        if( (article.getStock() + amount) < 0){
            throw new Exception( "Voorraad wordt negatief");
        }

        updateStock( article, amount);
    }

    @Override
    public void updateStock(Article article, int amount) {

        article.setStock( article.getStock() + amount);
        articleRepository.save( article);
    }
}
