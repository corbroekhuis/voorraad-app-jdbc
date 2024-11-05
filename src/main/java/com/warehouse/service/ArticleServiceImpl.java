package com.warehouse.service;

import com.warehouse.dao.article.ArticleDAO;
import com.warehouse.model.mapper.ArticleMapper;
import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService{

    ArticleDAO articleDAO;
    ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleDAO articleDAO, ArticleMapper articleMapper) {
        this.articleDAO = articleDAO;
        this.articleMapper = articleMapper;
    }

    @Override
    public Iterable<ArticleDTO> findAllDTOS() {

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        Iterable<Article> articles = articleDAO.findAll();

        for( Article article: articles){
            articleDTOS.add( articleMapper.toArticleDTO( article));
        }

        return articleDTOS;
    }

    @Override
    public ArticleDTO saveDTO(ArticleDTO articleDTO) {
        Article article = articleMapper.toArticle( articleDTO);

        if(articleDTO.getId() == null){
            // id is in article
            article = create( article);
        }else{
            int updated = articleDAO.update( article);
            System.out.println( "Updated: " + updated + " rows");
        }

        return articleMapper.toArticleDTO( article);
    }

    @Override
    public Optional<ArticleDTO> findDTOByEan(String ean) {
        Optional<Article> article = articleDAO.findByEan( ean);

        if( article.isPresent()){
            ArticleDTO articleDTO = articleMapper.toArticleDTO(article.get());
            return Optional.of( articleDTO);
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Article> findAll() {
        return articleDAO.findAll();
    }

    private Article create(Article article) {
        Number id = articleDAO.create( article);
        System.out.println( "Created Article with id: " + id.toString());
        article.setId( id.longValue());
        return article;
    }

    @Override
    public void deleteByEan(String ean){

        int deleted = articleDAO.deleteByEan( ean);
        System.out.println("Deleted " + deleted + " rows");
    }

    @Override
    public void updateStock(String ean, int amount) throws Exception {
        Optional<Article> optional = articleDAO.findByEan( ean);

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
        articleDAO.create( article);
    }
}
