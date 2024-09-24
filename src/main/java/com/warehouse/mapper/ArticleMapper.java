package com.warehouse.mapper;

import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public Article toArticle(ArticleDTO articleDTO) {

        Article article = new Article();
        article.setId( articleDTO.getId() );
        article.setName( articleDTO.getName() );
        article.setDescription( articleDTO.getDescription() );
        article.setEan( articleDTO.getEan() );
        article.setArticleNumber( articleDTO.getArticleNumber() );
        article.setStock( articleDTO.getStock());
        article.setMinimumStock( articleDTO.getMinimumStock());
        return article;
    }

    public ArticleDTO toArticle(Article article) {

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(  article.getId() );
        articleDTO.setName(  article.getName() );
        articleDTO.setDescription(  article.getDescription() );
        articleDTO.setEan(  article.getEan() );
        articleDTO.setArticleNumber(  article.getArticleNumber() );
        articleDTO.setStock(article.getStock());
        articleDTO.setMinimumStock(article.getMinimumStock());
        return articleDTO;
    }
}
