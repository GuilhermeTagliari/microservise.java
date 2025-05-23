package br.edu.atitus.productservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-service")
public interface CurrencyClient {

    @GetMapping("/currency/{value}/{source}/{target}")
    CurrencyResponse getCurrency(
            @PathVariable double value,
            @PathVariable String source,
            @PathVariable String target
    );

}
