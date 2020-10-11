package com.meifute.nm.others.config.swagger;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Swagger2Config
 * @Description Swagger2全局配置
 * @Date 2020-07-06 15:20
 * @Created by MR. Xb.Wu
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Value("${jenkinsApiVersion}")
    private String jenkinsApiVersion;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.or(
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.training.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.information.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.youxin.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.overseasstatistics.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.sourcematerial.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.itemtrial.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.generalactivities.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.userfeedback.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.datadictionary.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.logistics.controller"),
                        RequestHandlerSelectors.basePackage("com.meifute.nm.others.business.activitypay.controller")))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("分布式微服务商城系统-辅助功能模块v2.0")
                .description("美浮特商城接口说明文档")
                .termsOfServiceUrl("")
                .contact(new Contact("wuxb","wuxiabiao@meifute.com","wuxiabiao@meifute.com"))
                .version(jenkinsApiVersion)
                .build();
    }

    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("token密钥").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
