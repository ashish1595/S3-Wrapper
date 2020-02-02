package com.ashish.projects.s3;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ashish.projects.s3.utils.Constant;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(
						Arrays.asList(new ParameterBuilder()
								.name(Constant.Headers.ACCESS_KEY)
								.description("Organisation Access Key")
								.modelRef(new ModelRef("string"))
								.parameterType("header")
								.required(true)
								.build(),
								new ParameterBuilder()
								.name(Constant.Headers.ORGANISATION)
								.description("Organisation Name")
								.modelRef(new ModelRef("string"))
								.parameterType("header")
								.required(true)
								.build()
								))
				.select()                                  
				.apis(RequestHandlerSelectors.any())              
				.paths(PathSelectors.any())  
				.build();
	}
}