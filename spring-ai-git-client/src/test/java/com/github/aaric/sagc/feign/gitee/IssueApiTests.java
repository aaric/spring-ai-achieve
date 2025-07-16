package com.github.aaric.sagc.feign.gitee;

import com.github.aaric.sagc.config.GiteeProperties;
import com.github.aaric.sagc.feign.HttpClientFeignFactory;
import com.github.aaric.sagc.feign.dto.IssueDto;
import com.github.aaric.sagc.feign.enums.IssueStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final GiteeProperties giteeProperties;

    private final HttpClientFeignFactory httpClientFeignFactory;

    private IssueApi issueApi;

    @BeforeEach
    public void setUp() {
        issueApi = httpClientFeignFactory.createApi(IssueApi.class, giteeProperties.getEndpointUrl());
    }

    @Test
    public void testQueryIssueList() throws Exception {
        List<IssueDto> issueDtoList = issueApi.queryIssueList(giteeProperties.getAccessToken(),
                giteeProperties.getTestOwner(), giteeProperties.getTestRepo(), IssueStateEnum.ALL.getCode());
        System.err.println(issueDtoList);
    }

    @Deprecated
    @Test
    public void testCreateIssue() throws Exception {
        IssueDto issueForm = new IssueDto();
        issueForm.setRepo(giteeProperties.getTestRepo());
        issueForm.setTitle("hello world");
        IssueDto issueDto = issueApi.createIssue(giteeProperties.getAccessToken(), giteeProperties.getTestOwner(), issueForm);
        System.err.println(issueDto);
    }

    @Deprecated
    @Test
    public void testUpdateIssue() throws Exception {
        IssueDto issueForm = new IssueDto();
        issueForm.setRepo(giteeProperties.getTestRepo());
//        issueForm.setTitle("hello ai");
//        issueForm.setState(IssueStateEnum.PROGRESSING.getCode());
        issueForm.setBody("ai generate txt");
        IssueDto issueDto = issueApi.updateIssue(giteeProperties.getAccessToken(), giteeProperties.getTestOwner(), "ICLOOM", issueForm);
        System.err.println(issueDto);
    }
}
