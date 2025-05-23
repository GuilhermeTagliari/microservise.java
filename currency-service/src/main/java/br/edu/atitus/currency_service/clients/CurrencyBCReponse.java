package br.edu.atitus.currency_service.clients;

import java.util.List;

public class CurrencyBCReponse {
    private List<Currency> value;

    public List<Currency> getValue() {
        return value;
    }
    public void setValue(List<Currency> value) {
        this.value = value;
    }

}
