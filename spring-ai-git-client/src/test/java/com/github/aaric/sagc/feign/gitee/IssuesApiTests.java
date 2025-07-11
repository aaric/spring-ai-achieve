package com.github.aaric.sagc.feign.gitee;

import com.github.aaric.sagc.feign.HttpClientFeignFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * GitClientTests
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class IssuesApiTests {

    @Value("${git.gitee.endpoint-url}")
    private String endpointUrl;

    @Value("${git.gitee.access-token}")
    private String accessToken;

    @Value("${git.gitee.test-owner}")
    private String testOwner;

    @Value("${git.gitee.test-repo}")
    private String testRepo;

    private final HttpClientFeignFactory httpClientFeignFactory;

    @Test
    public void testIssues() throws Exception {
        IssuesApi issuesApi = httpClientFeignFactory.createApi(IssuesApi.class, endpointUrl);
        ResponseEntity<String> responseEntity = issuesApi.issues(accessToken, testOwner, testRepo);
        System.err.println(responseEntity.getBody());
    }
}
