package br.edu.atitus.order_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-service")
public interface CurrencyClient {
	
	@GetMapping("/currency/{value}/{source}/{target}")
	CurrencyResponse getCurrency(
			@PathVariable double value,
			@PathVariable String source,
			@PathVariable String target);
	

}
