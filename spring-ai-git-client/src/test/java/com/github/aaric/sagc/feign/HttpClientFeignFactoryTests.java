package com.github.aaric.sagc.feign;

import com.github.aaric.sagc.feign.gitee.IssueApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * OpenFeignClientFactoryTests
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class HttpClientFeignFactoryTests {

    private final HttpClientFeignFactory httpClientFeignFactory;

    @Test
    public void testCreateApi() {
        IssueApi issueApi = httpClientFeignFactory.createApi(IssueApi.class, "https://gitee.com/api/v5");
        System.err.println(issueApi);
    }
}
