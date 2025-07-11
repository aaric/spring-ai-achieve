package com.github.aaric.sagc.feign.gitee;

import com.github.aaric.sagc.feign.HttpClientFeignFactory;
import com.github.aaric.sagc.feign.dto.IssueDto;
import com.github.aaric.sagc.feign.enums.IssueStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
public class IssueApiTests {

    @Value("${git.gitee.endpoint-url}")
    private String endpointUrl;

    @Value("${git.gitee.access-token}")
    private String accessToken;

    @Value("${git.gitee.test-owner}")
    private String testOwner;

    @Value("${git.gitee.test-repo}")
    private String testRepo;

    private final HttpClientFeignFactory httpClientFeignFactory;

    private IssueApi issueApi;

    @BeforeEach
    public void setUp() {
        issueApi = httpClientFeignFactory.createApi(IssueApi.class, endpointUrl);
    }

    @Test
    public void testQueryIssueList() throws Exception {
        List<IssueDto> issueDtoList = issueApi.queryIssueList(accessToken, testOwner, testRepo, IssueStateEnum.ALL.getCode());
        System.err.println(issueDtoList);
    }

    @Test
    public void testCreateIssue() throws Exception {
        IssueDto issueForm = new IssueDto();
        issueForm.setRepo(testRepo);
        issueForm.setTitle("hello world");
        IssueDto issueDto = issueApi.createIssue(accessToken, testOwner, issueForm);
        System.err.println(issueDto);
    }

    @Test
    public void testUpdateIssue() throws Exception {
        IssueDto issueForm = new IssueDto();
        issueForm.setRepo(testRepo);
//        issueForm.setTitle("hello ai");
//        issueForm.setState("progressing");
        issueForm.setBody("ai generate txt");
        IssueDto issueDto = issueApi.updateIssue(accessToken, testOwner, "ICLOOM", issueForm);
        System.err.println(issueDto);
    }
}
