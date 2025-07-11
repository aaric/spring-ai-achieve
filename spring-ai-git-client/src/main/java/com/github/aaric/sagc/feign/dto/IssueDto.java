package com.github.aaric.sagc.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.Map;

/**
 * 问题 DTO
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Data
@Tag(name = "问题 DTO")
public class IssueDto {

    @Parameter(name = "问题ID")
    private Long id;

    @Parameter(name = "仓库路径")
    private String repo;

    @Parameter(name = "问题编号")
    private String number;

    @Parameter(name = "问题标题")
    private String title;

    @Parameter(name = "问题描述")
    private String body;

    @Parameter(name = "问题状态：open-开启的，progressing-进行中，closed-关闭的")
    private String state;

    @Parameter(name = "用户ID")
    private Long userId;

    @Parameter(name = "仓库ID")
    private Long repositoryId;

    @JsonProperty("user")
    public void setUserId(Map<String, Object> userMap) {
        this.userId = ((Number) userMap.get("id")).longValue();
    }

    @JsonProperty("repository")
    public void setRepositoryId(Map<String, Object> repositoryMap) {
        this.repositoryId = ((Number) repositoryMap.get("id")).longValue();
    }
}
