package com.example.Price_Compare.service;

import com.example.Price_Compare.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Product> fetchProducts(String query) {

        String API_URL;

        if (query == null || query.trim().isEmpty()) {
            API_URL = "https://dummyjson.com/products?limit=100";
        } else {
            API_URL = "https://dummyjson.com/products/search?q=" + query;
        }

        System.out.println("Calling DummyJSON API: " + API_URL);

        // Fetch JSON
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

        // DEBUG: print entire JSON response
            System.out.println("RAW JSON RESPONSE:");
            System.out.println(response);

        if (response == null) {
            System.out.println("❌ ERROR: DummyJSON returned NULL response");
            return new ArrayList<>();
        }

        // Extract "products" list
        List<Map<String, Object>> productsList =
                (List<Map<String, Object>>) response.get("products");

        if (productsList == null) {
            System.out.println("❌ ERROR: 'products' field missing in API JSON");
            return new ArrayList<>();
        }

        System.out.println("Products received: " + productsList.size());

        List<Product> result = new ArrayList<>();

        for (Map<String, Object> p : productsList) {

            Product product = new Product();

            product.setName((String) p.get("title"));
            product.setBrand((String) p.getOrDefault("brand", "Unknown"));
            product.setModel((String) p.getOrDefault("category", "Unknown"));

            product.setRating(
                    Double.parseDouble(p.get("rating").toString())
            );

            List<String> imgs = (List<String>) p.get("images");

            String image = (imgs != null && !imgs.isEmpty())
                    ? imgs.get(0)
                    : "https://via.placeholder.com/150";

            product.setImage(image);

            double priceDouble = Double.parseDouble(p.get("price").toString());
            int basePrice = (int) Math.round(priceDouble * 80);


            product.setPrices(List.of(
                    basePrice,
                    basePrice - 300,
                    basePrice + 300
            ));

            result.add(product);
        }

        return result;
    }
}
