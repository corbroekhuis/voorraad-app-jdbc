package com.warehouse.model.mapper;

import com.warehouse.component.EanGenerator;
import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ArticleMapper {

    EanGenerator eanGenerator;

    @Autowired
    public ArticleMapper(EanGenerator eanGenerator) {
        this.eanGenerator = eanGenerator;
    }

    public Article toArticle(ArticleDTO articleDTO) {

        Article article = new Article();
        article.setId( articleDTO.getId() );
        article.setName( articleDTO.getName() );
        article.setDescription( articleDTO.getDescription() );
        article.setEan( eanGenerator.newEan( articleDTO.getArticleNumber()));
        article.setArticleNumber( articleDTO.getArticleNumber() );
        article.setStock( articleDTO.getStock());
        article.setMinimumStock( articleDTO.getMinimumStock());
        return article;
    }

    public ArticleDTO toArticleDTO(Article article) {

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

//    private List<String> getReviews() {
//
//        String[] reviews = {"Ontzettend slecht product","Niet enthousiast","Gaat wel","Viel mee","Super product!!!"};
//        return Arrays.asList( reviews);
//    }
}
