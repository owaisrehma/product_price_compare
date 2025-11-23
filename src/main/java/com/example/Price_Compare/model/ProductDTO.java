package com.example.Price_Compare.model;

import java.util.List;

/**
 * DTO used for API-returned products (not a JPA entity).
 */
public class ProductDTO {
    private long id;
    private String name;
    private String brand;
    private String model;
    private double rating;
    private String image;
    private List<Integer> prices; // [amazon, flipkart, croma]

    public ProductDTO() {}

    public ProductDTO(long id, String name, String brand, String model, double rating, String image, List<Integer> prices) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.rating = rating;
        this.image = image;
        this.prices = prices;
    }

    // getters & setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public List<Integer> getPrices() { return prices; }
    public void setPrices(List<Integer> prices) { this.prices = prices; }
}
