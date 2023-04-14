package com.sso;

import com.sso.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SsoBeDg28Application {
	public static void main(String[] args) {
		SpringApplication.run(SsoBeDg28Application.class, args);
	}
}
