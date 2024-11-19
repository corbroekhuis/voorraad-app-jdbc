package com.warehouse.controller;

import com.warehouse.dao.article.ArticleDAO;
import com.warehouse.dao.review.ReviewDAO;
import com.warehouse.model.Article;
import com.warehouse.model.Review;
import com.warehouse.model.dto.ArticleDTO;
import com.warehouse.model.mapper.ArticleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ArticleControllerTest {

    @Autowired
    ArticleController articleController;

    @Autowired
    ArticleMapper articleMapper;

    @MockBean
    ArticleDAO articleDAO;

    @MockBean
    ReviewDAO reviewDAO;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup () {
        this.mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    public void findByEanReviewTest() throws Exception {

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setArticleNumber("1234");
        articleDTO.setStock(44);
        articleDTO.setMinimumStock(22);
        articleDTO.setName("Strijkijzer");
        articleDTO.setDescription("Veelzijdig strijkijzer");

        Article article = articleMapper.toArticle( articleDTO);

        Optional<ArticleDTO> optionalArticleDTO = Optional.of(articleDTO);

        when(articleDAO.findByEan(article.getEan()))
                .thenReturn(Optional.of(article));

        List<Review> reviews = new ArrayList<>();
        Review review =new Review();
        review.setId(1L);
        review.setArticleId(1L);
        review.setStars(3);
        review.setText("Prima aankoop, geen spijt");
        reviews.add( review);

        when(reviewDAO.findByArticleId(article.getId()))
                .thenReturn(reviews);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/findbyean2/{ean}", article.getEan()))
                .andDo(print())
                .andExpect(jsonPath("$.reviews.[0]").value("***: Prima aankoop, geen spijt"))
                .andExpect(status().isOk());

    }
}
