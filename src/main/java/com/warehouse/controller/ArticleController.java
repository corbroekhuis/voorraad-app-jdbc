package com.warehouse.controller;

import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ArticleController {

    ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    // http://localhost:8080/api/article
    @GetMapping(value = "article", produces = "application/json")
    public ResponseEntity<Iterable<ArticleDTO>> getArticles(){

        Iterable<ArticleDTO> articles = articleService.findAll();
        return ResponseEntity.ok(articles);
    }    
    
    // http://localhost:8080/api/article/3
    @GetMapping("article/{uuid}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id){
        Optional<ArticleDTO> article =  articleService.findById( id);

        if( article.isPresent()){
            return ResponseEntity.ok( article.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( null);
    }

    // http://localhost:8080/api/article
    @PostMapping(value = "article", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO article) {
        System.out.println( article);

        article = articleService.save( article);

        return ResponseEntity.ok(article);
    }

    @DeleteMapping(value="article/{id}")
    public String deleteArticle(@PathVariable Long id) {

        articleService.deleteById( id);
        return "deleted";
    }
}
