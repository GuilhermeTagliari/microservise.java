package br.edu.atitus.currency_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.currency_service.entities.CurrencyEntity;
import br.edu.atitus.currency_service.repositories.CurrencyRepository;

@RestController
@RequestMapping("currency")
public class CurrencyController {
	
	private final CurrencyRepository repository;
	
	public CurrencyController(CurrencyRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Value("${server.port}")
	private int serverPort;

	@GetMapping("/{value}/{source}/{target}")
	public ResponseEntity<CurrencyEntity> getCurrency(
			@PathVariable double value,
			@PathVariable String source,
			@PathVariable String target
			) throws Exception {
		CurrencyEntity currency = repository.findBySourceAndTarget(source, target)
				.orElseThrow(() -> new Exception("Currency not supported!!!"));
		
		
		currency.setConvertedValue(value * currency.getConversionRate());
		currency.setEnviroment("Currency-Service running on port: " + serverPort); 
		
		return ResponseEntity.ok(currency);
	}
	

}
