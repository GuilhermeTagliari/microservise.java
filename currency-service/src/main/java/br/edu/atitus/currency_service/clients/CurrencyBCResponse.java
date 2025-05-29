package br.edu.atitus.currency_service.clients;

import java.util.List;

public class CurrencyBCResponse {

	private List<CurrencyBC> value;

	public List<CurrencyBC> getValue() {
		return value;
	}

	public void setValue(List<CurrencyBC> value) {
		this.value = value;
	}

	public static class CurrencyBC {
		private double cotacaoVenda;

		public double getCotacaoVenda() {
			return cotacaoVenda;
		}

		public void setCotacaoVenda(double cotacaoVenda) {
			this.cotacaoVenda = cotacaoVenda;
		}
	}
}
