package com.github.aaric.sads;

import com.github.aaric.sads.config.HostProperties;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

/**
 * DockerClientTests
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DockerClientTests {

    private final HostProperties hostProperties;

    @Disabled
    @Test
    public void testDockerInfo() throws Exception {
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerTlsVerify(true)
                .withDockerHost("tcp://%s:%d".formatted(hostProperties.getIntranetIp(), hostProperties.getDockerPort()))
                .withDockerCertPath(hostProperties.getDockerCertsPath())
                .build();
        DockerHttpClient dockerHttpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(60))
                .responseTimeout(Duration.ofSeconds(120))
                .build();
        DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
        System.err.println(dockerClient.infoCmd().exec());
    }
}
