package com.spring.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

  /**
   * Rest template with custom cURL user-agent to be able to access SWAPI
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    RestTemplate restTemplate = builder.build();
    restTemplate.getInterceptors().add(addRequestHeader());
    return restTemplate;
  }

  private ClientHttpRequestInterceptor addRequestHeader() {
    return (request, body, execution) -> {
      request.getHeaders().set("User-agent", "curl/7.60.0");
      request.getHeaders().set("Content-Type", "application/json");
      request.getHeaders().set("Accept","application/json");
      return execution.execute(request, body);
    };
  }


}