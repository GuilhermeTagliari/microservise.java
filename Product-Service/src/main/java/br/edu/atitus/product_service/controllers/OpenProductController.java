package br.edu.atitus.product_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.product_service.entities.Product;
import br.edu.atitus.product_service.repositories.ProductRepository;

@RestController
@RequestMapping("/products")
public class OpenProductController {

    private final ProductRepository repository;

    public OpenProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/{idProduct}/{targetCurrency}")
    public ResponseEntity<Product> getProduct(@PathVariable Long idProduct, @PathVariable String targetCurrency) throws Exception {
        Product product = repository.findById(idProduct)
                .orElseThrow(() -> new Exception("Product not found!"));

        product.setConvertedPrice(product.getPrice());
        product.setEnvironment("Product-Service running on port: " + serverPort);

        return ResponseEntity.ok(product);
    }
}
