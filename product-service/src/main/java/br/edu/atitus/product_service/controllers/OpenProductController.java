package br.edu.atitus.product_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    public OpenProductController(ProductRepository repository, CurrencyClient currencyClient) {
        this.repository = repository;
        this.currencyClient = currencyClient;
    }

    @Value("${server.port}")
    private int serverPort;

    @Cacheable(value = "ProductCurrency", key = "#idProduct + '_' + #targetCurrency")
    @GetMapping("/{idProduct}/{targetCurrency}")
    public ResponseEntity<ProductEntity> getProduct(
            @PathVariable Long idProduct,
            @PathVariable String targetCurrency) throws Exception {

        ProductEntity product = repository.findById(idProduct)
                .orElseThrow(() -> new Exception("Product not found"));
        product.setEnviroment("Product-service running on Port: " + serverPort);

        if (targetCurrency.equals(product.getCurrency())) {
            product.setConvertedPrice(product.getPrice());
        } else {
            CurrencyResponse currency = currencyClient.getCurrency(product.getPrice(), product.getCurrency(), targetCurrency);
            product.setConvertedPrice(currency.getConvertedValue());
            product.setEnviroment(product.getEnviroment() + " - " + currency.getEnviroment());
        }

        return ResponseEntity.ok(product);
    }
}
