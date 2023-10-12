package com.demo.book;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class TestConfig {
	
	@Bean
	public TestRestTemplate testRestTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:8080");
		return new TestRestTemplate(restTemplateBuilder);
	}
}
