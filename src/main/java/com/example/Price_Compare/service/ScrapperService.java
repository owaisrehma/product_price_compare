package com.example.Price_Compare.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScrapperService {

    private static final String API_KEY = "8d273044dfaa1d6485faf1e271beddc1";

    public Map<String, Map<String, Object>> getLiveProductData(String query) {
        Map<String, Map<String, Object>> out = new HashMap<>();

        out.put("amazon", fetchAmazon(query));
        out.put("flipkart", fetchFlipkart(query));
        out.put("croma", fetchCroma(query));

        return out;
    }

    private Map<String, Object> fetchAmazon(String query) {
        Map<String, Object> data = new HashMap<>();
        try {
            String url = "https://api.scraperapi.com?api_key=" + API_KEY +
                    "&url=" + URLEncoder.encode("https://www.amazon.in/s?k=" + query, StandardCharsets.UTF_8);

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(15000).get();
            System.out.println("======== AMAZON HTML START ========");
            String html = doc.outerHtml();
            System.out.println(html.substring(0, Math.min(5000, html.length())));
            System.out.println("======== AMAZON HTML END ========");

            
            // Save HTML
            Files.writeString(Path.of("C:/Users/Public/amazon_debug.html"), doc.outerHtml());


            data.put("price", -1);
            data.put("title", "");
            data.put("url", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private Map<String, Object> fetchFlipkart(String query) {
        Map<String, Object> data = new HashMap<>();
        try {
            String url = "https://api.scraperapi.com?api_key=" + API_KEY +
                    "&url=" + URLEncoder.encode("https://www.flipkart.com/search?q=" + query, StandardCharsets.UTF_8);

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(15000).get();

                System.out.println("======== FLIPKART HTML START ========");
                String html = doc.outerHtml();
                System.out.println(html.substring(0, Math.min(5000, html.length())));
                System.out.println("======== FLIPKART HTML END ========");
            // Save HTML
            Files.writeString(Path.of("C:/Users/Public/flipkart_debug.html"), doc.outerHtml());


            data.put("price", -1);
            data.put("title", "");
            data.put("url", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private Map<String, Object> fetchCroma(String query) {
        Map<String, Object> data = new HashMap<>();
        try {
            String url = "https://api.scraperapi.com?api_key=" + API_KEY +
                    "&url=" + URLEncoder.encode("https://www.croma.com/search/?text=" + query, StandardCharsets.UTF_8);

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(15000).get();
            System.out.println("======== CROMA HTML START ========");
            String html = doc.outerHtml();
            System.out.println(html.substring(0, Math.min(5000, html.length())));
            System.out.println("======== CROMA HTML END ========");

            // Save HTML
            Files.writeString(Path.of("C:/Users/Public/croma_debug.html"), doc.outerHtml());


            data.put("price", -1);
            data.put("title", "");
            data.put("url", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
