package com.github.aaric.salc.config;

import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFlux 配置
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
@Deprecated
//@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        // 开启请求日志
        configurer.defaultCodecs().enableLoggingRequestDetails(true);

        // 自定义 SSE 编码器
        configurer.defaultCodecs().serverSentEventEncoder(CharSequenceEncoder.textPlainOnly());
    }
}
