package com.github.aaric.sagc.feign.start;

import cn.hutool.core.util.ZipUtil;
import com.github.aaric.sagc.feign.HttpClientFeignFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.zip.ZipInputStream;

/**
 * SpringInitializrApiTests
 *
 * @author Aaric
 * @version 0.7.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SpringInitializrApiTests {

    @Value("${spring.start.base-url}")
    private String baseUrl;

    private final HttpClientFeignFactory httpClientFeignFactory;

    private SpringInitializrApi springInitializrApi;

    @BeforeEach
    public void setUp() {
        springInitializrApi = httpClientFeignFactory.createApi(SpringInitializrApi.class, baseUrl);
    }

    @Test
    public void testStarterZip() throws Exception {
        String projectGroup = "com.example";
        String projectName = "demo";
        String projectDesc = "Demo project for Spring Boot";
        ResponseEntity<byte[]> responseEntity = springInitializrApi.starterZip("gradle-project", "java",
                "3.5.3", projectName, projectGroup, projectName, projectName, projectDesc,
                String.format("%s.%s", projectGroup, projectName.replaceAll("-", "").toLowerCase()),
                "jar", "17", "web");
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(responseEntity.getBody());
                 ZipInputStream iss = new ZipInputStream(bais)) {
                // 工作目录
                File destDir = new File("build/generated-project");
                if (!destDir.exists()) {
                    destDir.mkdir();
                }

                // 解压项目源文件
                ZipUtil.unzip(iss, destDir);
            }
        }
    }
}
