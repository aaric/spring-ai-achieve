package com.github.aaric.sagc.feign.gitee;

import com.github.aaric.sagc.feign.dto.IssueDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 问题 Api
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Tag(name = "问题 Api")
public interface IssueApi {

    @Operation(summary = "仓库的所有Issues")
    @GetMapping("/api/v5/repos/{owner}/{repo}/issues")
    List<IssueDto> queryIssueList(@Parameter(name = "access_token", description = "用户授权码") @RequestParam(name = "access_token") String accessToken,
                                  @Parameter(name = "owner", description = "仓库所属") @PathVariable(name = "owner") String owner,
                                  @Parameter(name = "repo", description = "仓库路径") @PathVariable(name = "repo") String repo,
                                  @Parameter(name = "state", description = "问题状态") @RequestParam(name = "state") String state);

    @Operation(summary = "创建Issue")
    @PostMapping("/api/v5/repos/{owner}/issues")
    IssueDto createIssue(@Parameter(name = "access_token", description = "用户授权码") @RequestParam(name = "access_token") String accessToken,
                         @Parameter(name = "owner", description = "仓库所属") @PathVariable(name = "owner") String owner,
                         @Parameter(name = "issueForm", description = "问题表单") @ModelAttribute(name = "issueForm") IssueDto issueForm);

    @Operation(summary = "更新Issue")
    @PatchMapping("/api/v5/repos/{owner}/issues/{number}")
    IssueDto updateIssue(@Parameter(name = "access_token", description = "用户授权码") @RequestParam(name = "access_token") String accessToken,
                         @Parameter(name = "owner", description = "仓库所属") @PathVariable(name = "owner") String owner,
                         @Parameter(name = "number", description = "问题编号") @PathVariable(name = "number") String number,
                         @Parameter(name = "issueForm", description = "问题表单") @ModelAttribute(name = "issueForm") IssueDto issueForm);
}
