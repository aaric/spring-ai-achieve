package com.github.aaric.sagc.service.impl;

import com.github.aaric.sagc.config.GiteeProperties;
import com.github.aaric.sagc.feign.HttpClientFeignFactory;
import com.github.aaric.sagc.feign.dto.IssueDto;
import com.github.aaric.sagc.feign.enums.IssueStateEnum;
import com.github.aaric.sagc.feign.gitee.IssueApi;
import com.github.aaric.sagc.service.IssueTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 问题任务 Service接口实现
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IssueTaskServiceImpl implements IssueTaskService {

    private final GiteeProperties giteeProperties;

    private final HttpClientFeignFactory httpClientFeignFactory;

    private final ChatClient chatClient;

    @Override
    public int doSimpleAiTips() {
        // 创建IssueApi
        IssueApi issueApi = httpClientFeignFactory.createApi(IssueApi.class, giteeProperties.getEndpointUrl());

        // 查找创建的问题列表
        List<IssueDto> openIssueDtoList = issueApi.queryIssueList(giteeProperties.getAccessToken(),
                giteeProperties.getTestOwner(), giteeProperties.getTestRepo(), IssueStateEnum.OPEN.getCode());
        if (!CollectionUtils.isEmpty(openIssueDtoList)) {
            // 改变状态并简单AI回复
            for (IssueDto issueDto : openIssueDtoList) {
                // 创建AI回复
                String aiText = chatClient.prompt().user(issueDto.getTitle()).call().content();
                log.info("doSimpleAiTips -> number={}, aiText={}", issueDto.getNumber(), aiText);

                // 修改描述并状态
                IssueDto issueForm = new IssueDto();
                issueForm.setRepo(giteeProperties.getTestRepo());
                issueForm.setState("progressing");
                issueForm.setBody(aiText);
                issueApi.updateIssue(giteeProperties.getAccessToken(), giteeProperties.getTestOwner(),
                        issueDto.getNumber(), issueForm);
            }

            return openIssueDtoList.size();
        }

        return 0;
    }
}
