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

    ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping( value = "/article", produces = "application/json")
    public ResponseEntity<Iterable<ArticleDTO>> findAll(){

        Iterable<ArticleDTO> articleDTOS = articleService.findAll();

        return ResponseEntity.ok(articleDTOS);
    }

    @PostMapping( value = "/article", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO){

        ArticleDTO saved = articleService.save( articleDTO);

        return ResponseEntity.ok(saved);
    }
    // ...findbyean?ean=82389348935
    @GetMapping( value = "/findbyean", produces = "application/json")
    public ResponseEntity<ArticleDTO> findByEan(@RequestParam String ean){
        Optional<ArticleDTO> articleDTO = articleService.findByEan( ean);

        if(articleDTO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(articleDTO.get());
    }



}
