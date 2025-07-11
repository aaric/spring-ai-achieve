package com.github.aaric.sagc.feign;

import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.httpclient.ApacheHttpClient;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 基于 ApacheHttpClient 的 OpenFeign 客户端工厂类
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Component
@RequiredArgsConstructor
@Import(FeignClientsConfiguration.class)
public class HttpClientFeignFactory {

    private final Decoder decoder;
    private final Encoder encoder;
    private final Contract contract;

    /**
     * 创建API客户端实例
     *
     * @param apiClazz    API定义
     * @param endpointUrl API接入地址
     * @param <T>         支持泛型
     * @return API实例
     */
    public <T> T createApi(Class<T> apiClazz, String endpointUrl) {
        return createApi(apiClazz, endpointUrl, null);
    }

    /**
     * 创建API客户端实例
     *
     * @param apiClazz     API定义
     * @param endpointUrl  API接入地址
     * @param interceptors 拦截器
     * @param <T>          支持泛型
     * @return API实例
     */
    public <T> T createApi(Class<T> apiClazz, String endpointUrl, Set<RequestInterceptor> interceptors) {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
                //.setSSLSocketFactory(getSSLConnectionSocketFactory())
                .build();
        ApacheHttpClient httpClient = new ApacheHttpClient(closeableHttpClient);

        Feign.Builder builder = Feign.builder()
                .decoder(decoder)
                .encoder(encoder)
                .contract(contract)
                .client(httpClient);

        if (null != interceptors && !interceptors.isEmpty()) {
            builder.requestInterceptors(interceptors);
        }

        return builder.target(apiClazz, endpointUrl);
    }
}
