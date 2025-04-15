package br.edu.atitus.greating_service.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("greating-service")
public class GreatingConfig {
	
	private String greating;
	private String defaultName;
	public String getGreating() {
		return greating;
	}
	public void setGreating(String greating) {
		this.greating = greating;
	}
	public String getDefaultName() {
		return defaultName;
	}
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}
	
	

}
