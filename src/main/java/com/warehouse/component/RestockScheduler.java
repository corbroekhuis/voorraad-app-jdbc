package com.warehouse.component;

import com.warehouse.model.Article;
import com.warehouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class RestockScheduler {

    ArticleService articleService;

    @Autowired
    public RestockScheduler(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Scheduled(fixedDelay = 10_000)
    //@Scheduled(fixedRate = 1000)
    //@Scheduled(cron = "0 15 10 15 * ?")
    public void RestockTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);

        Iterable<Article> articles = articleService.findAll();

        for( Article article: articles){
            if( article.getStock() < article.getMinimumStock()){
                article.setStock( article.getStock() + 20);
                articleService.save( article);
                System.out.println("Voorraad opgehoogd voor artikel: " + article.getEan());
            }
        }
    }
}
