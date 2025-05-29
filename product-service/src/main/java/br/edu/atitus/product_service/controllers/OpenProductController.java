package br.edu.atitus.product_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.product_service.clients.CurrencyClient;
import br.edu.atitus.product_service.clients.CurrencyResponse;
import br.edu.atitus.product_service.entities.ProductEntity;
import br.edu.atitus.product_service.repositories.ProductRepository;

@RestController
@RequestMapping("products")
public class OpenProductController {

    private final ProductRepository repository;
    private final CurrencyClient currencyClient;
    private final CacheManager cacheManager;

    public OpenProductController(ProductRepository repository, CurrencyClient currencyClient, CacheManager cacheManager) {
        this.repository = repository;
        this.currencyClient = currencyClient;
        this.cacheManager = cacheManager;
    }

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/{idProduct}/{targetCurrency}")
    public ResponseEntity<ProductEntity> getProduct(
            @PathVariable Long idProduct,
            @PathVariable String targetCurrency) throws Exception {

        ProductEntity product = repository.findById(idProduct)
                .orElseThrow(() -> new Exception("Product not found"));

        product.setEnviroment("Product-service running on Port: " + serverPort);

        if (targetCurrency.equalsIgnoreCase(product.getCurrency())) {
            product.setConvertedPrice(product.getPrice());
        } else {
            String cacheKey = idProduct + "_" + targetCurrency;
            String cacheName = "ProductCurrency";

            CurrencyResponse currency = cacheManager.getCache(cacheName).get(cacheKey, CurrencyResponse.class);

            if (currency != null) {
                product.setConvertedPrice(currency.getConvertedValue());
                product.setEnviroment(product.getEnviroment() + " - Currency from Cache");
            } else {
                currency = currencyClient.getCurrency(product.getPrice(), product.getCurrency(), targetCurrency);
                product.setConvertedPrice(currency.getConvertedValue());
                product.setEnviroment(product.getEnviroment() + " - " + currency.getEnviroment());
                cacheManager.getCache(cacheName).put(cacheKey, currency);
            }
        }

        return ResponseEntity.ok(product);
    }
}
