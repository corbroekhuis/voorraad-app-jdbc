package com.warehouse.component;

import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ArticleMapper {

    @Autowired
    EanGenerator eanGenerator;

    public ArticleMapper(EanGenerator eanGenerator){
        this.eanGenerator = eanGenerator;
    }

    public ArticleDTO toDto(Article article) {

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId( article.getId());
        articleDTO.setName( article.getName());
        articleDTO.setArticleNumber(article.getArticleNumber());
        articleDTO.setEan(article.getEan());
        articleDTO.setMinStock(article.getMinStock());
        articleDTO.setStock(article.getStock());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setReviews( getReviews());

        return articleDTO;
    }

    private List<String> getReviews() {
        String[] reviews = {"Viel erg tegen","Was al na een dag stuk","Matig tevreden","Best ok","Beste aankoop ooit"};
        return Arrays.asList( reviews);
    }


    public Article toArticle(ArticleDTO articleDTO) {

        Article article = new Article();
        article.setName( articleDTO.getName());
        article.setId( articleDTO.getId());
        article.setArticleNumber(articleDTO.getArticleNumber());
        article.setEan( eanGenerator.newEan( article.getArticleNumber()));
        article.setMinStock(0);
        article.setStock(0);
        article.setDescription(articleDTO.getDescription());

        return article;
    }
}
