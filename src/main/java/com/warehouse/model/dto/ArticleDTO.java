package com.warehouse.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ArticleDTO {

    private Long id;
    private String name;
    private String articleNumber;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String ean;
    @JsonIgnore
    private int stock;
    @JsonIgnore
    private int minStock;
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}
