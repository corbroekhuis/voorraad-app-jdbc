package com.warehouse.controller;

import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // http://localhost:8080/api/article
    @GetMapping( value = "/article", produces = "application/json")
    public ResponseEntity<Iterable<ArticleDTO>> findAll(){

        Iterable<ArticleDTO> articleDTOS = articleService.findAllDTOS();

        return ResponseEntity.ok(articleDTOS);
    }

    // http://localhost:8080/api/article
    @PostMapping( value = "/article", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO){

        ArticleDTO saved = articleService.saveDTO( articleDTO);

        return ResponseEntity.ok(saved);
    }
    // http://localhost:8080/api/findbyean?ean=82389348935
    @GetMapping( value = "/findbyean", produces = "application/json")
    public ResponseEntity<ArticleDTO> findByEan(@RequestParam String ean){
        Optional<ArticleDTO> articleDTO = articleService.findDTOByEan( ean);

        if(articleDTO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(articleDTO.get());
    }

    // http://localhost:8080/api/findbyean?ean=82389348935
    @GetMapping( value = "/adjuststock", produces = "application/json")
    public ResponseEntity<Article> findByEan(@RequestParam String ean, @RequestParam int amount){

        Article article = articleService.adjustStock( ean, amount);

        return ResponseEntity.ok(article);
    }



}
