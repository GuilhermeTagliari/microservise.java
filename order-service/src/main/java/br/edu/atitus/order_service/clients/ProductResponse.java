package br.edu.atitus.order_service.clients;

public record ProductResponse(
	    Long id,
	    String description,
	    String brand,
	    String model,
	    double price,
	    String currency,
	    Integer stock,
	    String imageUrl,
	    String enviroment,
	    double convertedPrice
	) {}
