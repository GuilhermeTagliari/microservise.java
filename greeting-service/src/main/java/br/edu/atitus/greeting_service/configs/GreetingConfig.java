package br.edu.atitus.greeting_service.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("greeting-service")
public class GreetingConfig {
	
	private String greeting;
	private String defaultName;
	
	public String getGreating() {
		return greeting;
	}
	public void setGreating(String greating) {
		this.greeting = greating;
	}
	public String getDefaultName() {
		return defaultName;
	}
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}
	
	

}
