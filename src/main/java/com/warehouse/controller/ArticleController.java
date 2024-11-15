package com.warehouse.controller;

import com.warehouse.model.Article;
import com.warehouse.model.client.ArticleSER;
import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // http:/<port>/api/article
    @GetMapping( value = "/article", produces = "application/json")
    public ResponseEntity<Iterable<ArticleDTO>> findAll(){

        Iterable<ArticleDTO> articleDTOS = articleService.findAllDTOS();

        return ResponseEntity.ok(articleDTOS);
    }

    // http:/<port>/api/article
    @PostMapping( value = "/article", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create/Update voor een artikel",
            description= """
                <ul>
                    <li> Voeg een nieuw artikel toe of update een bestaand artikel</li>
                    <li> Als er geen id is gespecificeerd in de json dan wordt een nieuw artikel aangemaakt</li>
                    <li> Als er wel een id is gespecificeerd dan wordt het bestaande artikel geupdate </li>
                    <li> Voeg in beide gevallen geen ean toe; dat wordt gegenereerd</li>
                    <li> Nota Bene: kies een niet bestaand artikelnummer</li>
                </ul>
                """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bericht toegevoegd",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class),
                            examples = {
                                    @ExampleObject(
                                            name = "create",
                                            summary = "Voeg een artikel toe",
                                            description = """
                                                    Deze voorbeeld JSON, een nieuw artikel toe<br>
                                                    met artikelnummer '50011'<br>
                                                    het EAN nummer wordt gegenereerd<br>
                                                    Het artikelnummer mag nog niet bestaan in de tabel<br>
                                                    """,
                                            value = """
                                                      {
                                                        "name": "Fat Bike B",
                                                        "description": "Black Fat Bike",
                                                        "articleNumber": "50011",
                                                        "stock": 20,
                                                        "minimumStock": 10
                                                      }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "update",
                                            summary = "Update een bestaand artikel",
                                            description = """
                                                    Deze voorbeeld JSON, update het artikel met id = 7<br>
                                                    """,
                                            value = """
                                                      {
                                                        "id": 7,
                                                        "name": "Fat Bike B",
                                                        "description": "Black Fat Bike updated",
                                                        "articleNumber": "50011",
                                                        "stock": 50,
                                                        "minimumStock": 10
                                                      }
                                                    """
                                    )
                            }),
                    }),
            @ApiResponse(responseCode = "409", description = "HTTP status 409: Conflict, artikel met zelfde artikelnummer bestaat al",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class),
                            examples = {
                                    @ExampleObject(
                                            name = "example-400",
                                            summary = "Artikelnummer bestaat al",
                                            description = """
                                                    Deze voorbeeld JSON, voegt een artikel toe<br>
                                                    echter, artikelnummer 50011 bestaat al<br>
                                                    er wordt geen waarde teruggegeven<br>
                                                    """,
                                            value = """
                                                      {
                                                        "name": "Fat Bike B",
                                                        "description": "Black Fat Bike",
                                                        "articleNumber": "50011",
                                                        "stock": 20,
                                                        "minimumStock": 10
                                                      }
                                                    """
                                    )
                            }),
                    })
    })
    public ResponseEntity<ArticleDTO> saveArticle(@RequestBody ArticleDTO articleDTO){

        ArticleDTO saved = null;

        try {
            saved = articleService.saveDTO( articleDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.ok(saved);
    }

    // http:/<port>/api/findbyean?ean=123456789
    @GetMapping( value = "/findbyean", produces = "application/json")
    public ResponseEntity<ArticleDTO> findByEan(@RequestParam String ean){
        Optional<ArticleDTO> articleDTO = articleService.findDTOByEan( ean);

        if(articleDTO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            return ResponseEntity.ok(articleDTO.get());
        }
    }

    // http:/<port>/api/findbyean2/123456789
    @GetMapping( value = "/findbyean2/{ean}", produces = "application/json")
    public ResponseEntity<ArticleDTO> findByEan2(@PathVariable String ean){
        Optional<ArticleDTO> articleDTO = articleService.findDTOByEan( ean);

        if(articleDTO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            return ResponseEntity.ok(articleDTO.get());
        }
    }

    // http:/<port>/api/updatestock?ean=123456789&quantity=10
    @PostMapping( value = "/updatestockbyean")
    public ResponseEntity<String> updateStockByEan(@RequestParam String ean, @RequestParam int quantity){

        try {
            articleService.updateStock( ean, quantity);
        } catch (Exception e) {
            System.out.println( e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok("updated");
    }

    // http:/<port>/api/updatestockbyid?id=123456789&quantity=10
    @PostMapping( value = "/updatestockbyid")
    public ResponseEntity<String> updateStockById(@RequestParam long id, @RequestParam int quantity){

        try {
            articleService.updateStockById( id, quantity);
        } catch (Exception e) {
            System.out.println( e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.ok("updated");
    }

    // http:/<port>/api/article/123456789
    @DeleteMapping( value="/article")
    public ResponseEntity<String> deleteByEan( @RequestParam long id){

        articleService.deleteById( id);
        return ResponseEntity.ok("deleted");
    }

    // http:/<port>/api/article
    @GetMapping( value = "/articleser", produces = "application/json")
    public ResponseEntity<Iterable<ArticleSER>> findAllSER(){

        Iterable<ArticleSER> articleSERS = articleService.findAllSERS();
        return ResponseEntity.ok(articleSERS);
    }

    // http:/<port>/api/article
    @GetMapping( value = "/articleser/{id}", produces = "application/json")
    public ResponseEntity<ArticleSER> findSERById( @PathVariable("id") Long id){

        Optional<ArticleSER> articleSER = articleService.findSERById( id);

        if(articleSER.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            return ResponseEntity.ok(articleSER.get());
        }
    }
}
