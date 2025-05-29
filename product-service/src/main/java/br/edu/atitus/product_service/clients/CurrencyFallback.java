package br.edu.atitus.product_service.clients;

import org.springframework.stereotype.Component;

@Component
public class CurrencyFallback implements CurrencyClient {
    
    @Override
    public CurrencyResponse getCurrency(double value, String source, String target) {
        CurrencyResponse fallback = new CurrencyResponse();
        fallback.setSource(source);
        fallback.setTarget(target);
        fallback.setConvertedValue(-1);
        fallback.setEnviroment("Fallback response: Currency service unavailable");
        return fallback;
    }
}
