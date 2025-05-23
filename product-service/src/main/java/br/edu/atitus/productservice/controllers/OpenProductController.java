package br.edu.atitus.productservice.controllers;

import br.edu.atitus.productservice.clients.CurrencyClient;
import br.edu.atitus.productservice.clients.CurrencyResponse;
import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class OpenProductController {

    private final ProductRepository productRepository;
    private final CurrencyClient currencyClient;

    public OpenProductController(ProductRepository productRepository, CurrencyClient currencyClient) {
        this.productRepository = productRepository;
        this.currencyClient = currencyClient;
    }

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/{idProduct}/{targetCurrency}")
    public ResponseEntity<ProductEntity> getById(@PathVariable Long idProduct, @PathVariable String targetCurrency) throws Exception {

        ProductEntity product = productRepository.findById(idProduct)
                .orElseThrow(() -> new Exception("Product not found"));

        product.setEnvironment("" + serverPort);

        if (targetCurrency.equals(product.getCurrency())) {
            product.setConvertedPrice(product.getPrice());
        } else {
            CurrencyResponse currency = currencyClient.getCurrency(product.getPrice(), product.getCurrency(), targetCurrency);
            product.setEnvironment(product.getEnvironment() + " " + currency.getEnvironment());
            product.setConvertedPrice(currency.getConvertedValue());
        }

        return ResponseEntity.ok(product);
    }


}
