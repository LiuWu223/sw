package com.lw.swTest.core.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Swagger接口文档配置
 * @author xiefan
 * @since 2022-11-15
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.lw.swTest"})
public class SwaggerConfig {
    @Bean
    public Docket api() {
        Set<String> protocols = new HashSet<>();
        protocols.add("https");
        protocols.add("http");
        return new Docket(DocumentationType.SWAGGER_2)
                // 限定通讯协议集合
                .protocols(protocols)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo())
                // 选择哪些路径和API会生成document
                .select()
                // 扫描指定包中的swagger注解
                 .apis(RequestHandlerSelectors.basePackage("com.lw"))
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描所有的api(没有添加注解也可以扫描出来),用这种方式更直接
                // .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 这是匹配api的信息
     *
     * @return ApiInfoBuilder
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("接口测试")
                // 创建人     个人信息地址    邮箱
                .contact(new Contact("lw", "", "123@qq.com"))
                // 版本号
                .version("1.0")
                // 描述
                .description("auto_sw-API接口")
                .build();
    }

    /**
     * 自定义一个Apikey
     * 这是一个包含在header中键名为Authorization的JWT标识
     */
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * 配置JWT的SecurityContext 并设置全局生效
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
}
