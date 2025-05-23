package br.edu.atitus.currency_service.controllers;

import br.edu.atitus.currency_service.clients.CurrencyBCClient;
import br.edu.atitus.currency_service.clients.CurrencyBCReponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.currency_service.entities.CurrencyEntity;
import br.edu.atitus.currency_service.repositories.CurrencyRepository;

import java.util.Locale;

@RestController
@RequestMapping("currency")
public class CurrencyController {
	
	private final CurrencyRepository repository;
	private final CurrencyBCClient currencyBCClient;

	public CurrencyController(CurrencyRepository repository, CurrencyBCClient currencyBCClient) {
		this.repository = repository;
		this.currencyBCClient = currencyBCClient;
	}
	
	@Value("${server.port}")
	private int serverPort;

	@GetMapping("/{value}/{source}/{target}")
	public ResponseEntity<CurrencyEntity> getCurrency(
			@PathVariable double value,
			@PathVariable String source,
			@PathVariable String target
			) throws Exception {

		CurrencyEntity currency;

		if (source.equals("BRL")) {
			CurrencyBCReponse response = currencyBCClient.getCurrencyBC(target);

			if (response.getValue().isEmpty()) {
				throw new Exception("Nenhuma resposa retornada do banco central");
			}

			double cotacaoVenda = response.getValue().get(0).getCotacaoVenda();

			currency = new CurrencyEntity();
			currency.setSource(source);
			currency.setTarget(target);
			currency.setConversionRate(cotacaoVenda);
		} else {
			currency = repository.findBySourceAndTarget(source, target)
					.orElseThrow(() -> new Exception("Currency not supported!!!"));
		}

		currency.setConvertedValue(value * currency.getConversionRate());
		currency.setEnvironment(""+serverPort);

		return ResponseEntity.ok(currency);
	}
	

}
