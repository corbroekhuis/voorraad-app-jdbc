package com.warehouse.controller;

import com.warehouse.model.Article;
import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
            @ApiResponse(responseCode = "400", description = "Bad Request, artikel met zelfde artikelnummer bestaat al",
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

    // http:/<port>/api/article?ean=123456789&amount=10
    @PostMapping( value = "/updatestock")
    public ResponseEntity<String> updateStock(@RequestParam String ean, @RequestParam int amount){

        try {
            articleService.updateStock( ean, amount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok("updated");
    }

    // http:/<port>/api/article?ean=123456789
    @DeleteMapping( value="/article")
    public ResponseEntity<String> deleteByEan( @RequestParam String ean){

        articleService.deleteByEan( ean);
        return ResponseEntity.ok("deleted");
    }
}
