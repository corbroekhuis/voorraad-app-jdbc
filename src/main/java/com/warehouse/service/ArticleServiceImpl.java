package com.warehouse.service;

import com.warehouse.dao.article.ArticleDAO;
import com.warehouse.dao.review.ReviewDAO;
import com.warehouse.model.Review;
import com.warehouse.model.client.ArticleSER;
import com.warehouse.util.Util;
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
    ReviewDAO reviewDAO;
    ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleDAO articleDAO,
                              ReviewDAO reviewDAO,
                              ArticleMapper articleMapper) {
        this.articleDAO = articleDAO;
        this.reviewDAO = reviewDAO;
        this.articleMapper = articleMapper;
    }

    @Override
    public Iterable<ArticleDTO> findAllDTOS() {

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        Iterable<Article> articles = articleDAO.findAll();

        for( Article article: articles){

            ArticleDTO articleDTO = articleMapper.toArticleDTO( article);
            List<Review> reviews = reviewDAO.findByArticleId( articleDTO.getId());
            List<String> formattedReviews = formatReviews( reviews);
            articleDTO.setReviews( formattedReviews);
            articleDTOS.add( articleDTO );
        }

        return articleDTOS;
    }

    private List<String> formatReviews(List<Review> reviews) {
        List<String> formattedReviews = new ArrayList<>();

        for( Review review: reviews){
            formattedReviews.add( Util.getFormattedStars( review.getStars()) + ": " + review.getText() );
        }

        return formattedReviews;
    }

    @Override
    public ArticleDTO saveDTO(ArticleDTO articleDTO) throws Exception {

        Optional<Article> found = articleDAO.findByArticleNumber( articleDTO.getArticleNumber());

        if(articleDTO.getId() == null && found.isPresent()){
            throw new Exception("Artikel met artikelnummer: " + articleDTO.getArticleNumber() + " bestaat al");
        }

        Article article = articleMapper.toArticle( articleDTO);

        if(articleDTO.getId() == null){
            article = create( article);
        }else{
            // id is in article
            int updated = articleDAO.update( article);
            System.out.println( "Updated: " + updated + " rows");
        }

        return articleMapper.toArticleDTO( article);
    }

    private Article create(Article article) {
        Number id = articleDAO.create( article);
        System.out.println( "Created Article with id: " + id.toString());
        article.setId( id.longValue());
        return article;
    }

    @Override
    public Optional<ArticleDTO> findDTOByEan(String ean) {
        Optional<Article> article = articleDAO.findByEan( ean);

        if( article.isPresent()){
            ArticleDTO articleDTO = articleMapper.toArticleDTO(article.get());

            List<Review> reviews = reviewDAO.findByArticleId(article.get().getId());
            List<String> formattedReviews = formatReviews( reviews);
            articleDTO.setReviews( formattedReviews);

            return Optional.of( articleDTO);
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Article> findAll() {
        return articleDAO.findAll();
    }

    @Override
    public void deleteById(long id){

        int deleted = articleDAO.deleteById( id);
        System.out.println("Deleted " + deleted + " rows");
    }

    @Override
    public void updateStock(String ean, int quantity) throws Exception {
        Optional<Article> optional = articleDAO.findByEan( ean);

        if(optional.isEmpty()){
            throw new Exception( "Artikel niet gevonden");
        }

        Article article = optional.get();

        if( (article.getStock() + quantity) < 0){
            throw new Exception( "Voorraad wordt negatief");
        }

        updateStock( article, quantity);
    }

    @Override
    public void updateStock(Article article, int quantity) {

        article.setStock( article.getStock() + quantity);
        articleDAO.update( article);
    }

    @Override
    public Iterable<ArticleSER> findAllSERS() {

        List<ArticleSER> articleSERS = new ArrayList<>();
        Iterable<Article> articles = articleDAO.findAll();

        for( Article article: articles){
            ArticleSER articleSER = new ArticleSER();
            articleSER.setId(article.getId());
            articleSER.setName(article.getName());
            articleSER.setDescription(article.getDescription());
            List<Review> reviews = reviewDAO.findByArticleId(article.getId());
            List<String> formattedReviews = formatReviews( reviews);
            articleSER.setReviews( formattedReviews);
            articleSERS.add( articleSER);
        }

        return articleSERS;
    }

    @Override
    public Optional<ArticleSER> findSERById(long id) {

        Optional<Article> articleOptional = articleDAO.findById( id);

        if(articleOptional.isEmpty()){
            return Optional.empty();
        }

        Article article = articleOptional.get();

        ArticleSER articleSER = new ArticleSER();
        articleSER.setId(article.getId());
        articleSER.setName(article.getName());
        articleSER.setDescription(article.getDescription());

        List<Review> reviews = reviewDAO.findByArticleId(article.getId());
        List<String> formattedReviews = formatReviews( reviews);
        articleSER.setReviews( formattedReviews);
        return Optional.of(articleSER);
    }

    @Override
    public void updateStockById(long id, int quantity) throws Exception {
        Optional<Article> optional = articleDAO.findById( id);

        if(optional.isEmpty()){
            throw new Exception( "Artikel niet gevonden");
        }

        Article article = optional.get();

        if( (article.getStock() + quantity) < 0){
            throw new Exception( "Voorraad wordt negatief");
        }

        updateStock( article, quantity);

    }
}
