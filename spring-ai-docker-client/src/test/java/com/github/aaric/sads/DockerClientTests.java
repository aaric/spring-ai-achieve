package com.github.aaric.sads;

import com.github.aaric.sads.config.HostProperties;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private DockerClient dockerClient;

    @BeforeEach
    public void setUp() {
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerTlsVerify(true)
                .withDockerHost("tcp://%s:%d".formatted(hostProperties.getIntranetIp(), hostProperties.getDockerPort()))
                .withDockerCertPath(hostProperties.getDockerCertsPath())
//                .withApiVersion("1.51")
                .build();
        DockerHttpClient dockerHttpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(60))
                .responseTimeout(Duration.ofSeconds(120))
                .build();
        dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
    }

    @Disabled
    @Test
    public void testDockerInfo() throws Exception {
        System.err.println(dockerClient.infoCmd().exec());
    }

    @Disabled
    @Test
    public void testPullImage() throws Exception {
        List<Image> imageList = dockerClient.listImagesCmd().withImageNameFilter("hello-world").exec();
        if (imageList.isEmpty()) {
            dockerClient.pullImageCmd("hello-world")
                    .withTag("latest")
                    .exec(new PullImageResultCallback() {
                        @Override
                        public void onNext(PullResponseItem item) {
                            log.info("Pulling: {}", item.getStatus());
                            super.onNext(item);
                        }
                    })
                    .awaitCompletion();
        }

        System.err.println("pull image ok");
    }

    @Disabled
    @Test
    public void testCreateContainer() throws Exception {
        String containerId;
        String containerName = "my-hello-world";
        List<Container> containerList = dockerClient.listContainersCmd()
                .withShowAll(true)
                .withNameFilter(Arrays.asList(containerName))
                .exec();

        if (null != containerList && !containerList.isEmpty()) {
            containerId = containerList.get(0).getId();
        } else {
            CreateContainerResponse response = dockerClient.createContainerCmd("hello-world")
                    .withName(containerName)
                    .exec();
            containerId = response.getId();
        }
        System.err.println("containerId: " + containerId);

        dockerClient.startContainerCmd(containerId).exec();

        dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(true)
                .exec(new ResultCallback.Adapter<Frame>() {
                    @Override
                    public void onNext(Frame frame) {
                        System.out.println(new String(frame.getPayload()));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("获取日志出错: " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("日志读取完成");
                    }
                })
                .awaitCompletion(10, TimeUnit.SECONDS);

        dockerClient.startContainerCmd(containerId).exec();
        dockerClient.removeContainerCmd(containerId)
                .withForce(true)
                .withRemoveVolumes(true)
                .exec();
    }
}
