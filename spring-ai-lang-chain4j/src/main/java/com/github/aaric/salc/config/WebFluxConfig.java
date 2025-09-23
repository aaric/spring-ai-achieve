package com.github.aaric.salc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFlux 配置
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    /*@Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        // 开启请求日志
        configurer.defaultCodecs().enableLoggingRequestDetails(true);

        // 自定义 SSE 编码器
        configurer.defaultCodecs().serverSentEventEncoder(CharSequenceEncoder.textPlainOnly());
    }*/

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:8082")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600L);
    }
}
