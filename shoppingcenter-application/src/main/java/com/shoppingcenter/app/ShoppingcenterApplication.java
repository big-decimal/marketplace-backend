package com.shoppingcenter.app;

import java.util.TimeZone;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.shoppingcenter.app.common.DefaultAppProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "com.shoppingcenter" })
@EnableAsync
@EnableScheduling
@EnableRetry
@EnableBatchProcessing
@EnableConfigurationProperties(DefaultAppProperties.class)
public class ShoppingcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingcenterApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		// System.out.println("Temp dir: " + System.getProperty("java.io.tmpdir"));
	}

	// @Bean
	OpenAPI customOpenAPI() {
		String securitySchemeName = "bearerAuth";
		Info info = new Info()
				.title("Shopping Center API")
				.version("1.0")
				.description("This is a shopping center server created using springdocs - " +
						"a library for OpenAPI 3 with spring boot.")
				.termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0")
						.url("http://springdoc.org"));

		Components components = new Components()
				.addSecuritySchemes(securitySchemeName,
						new SecurityScheme()
								.name(securitySchemeName)
								.type(SecurityScheme.Type.HTTP)
								.scheme("Bearer")
								.bearerFormat("JWT"));
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(components)
				.info(info);
	}

}
