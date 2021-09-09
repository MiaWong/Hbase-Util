package com.xsyx.test.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName : SwaggerConfig
 * @Description : com.xsyx.test.common.config
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/19 9:49
 * @Version 1.0
 **/
@EnableSwagger2
@Configuration
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true")
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xsyx.test"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Test tool")
                .description("测试工具-接口文档")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0.0-SNAPSHOT")
                .contact(new Contact("WangMiao-王淼", "", "wangmiao@hn.xsyxsc.cn"))
                .build();
    }
}