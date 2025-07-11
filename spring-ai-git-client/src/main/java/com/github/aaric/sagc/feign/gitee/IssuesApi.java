package com.github.aaric.sagc.feign.gitee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Issues Api
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Tag(name = "Issues Api")
public interface IssuesApi {

    @Operation(summary = "仓库的所有Issues")
    @GetMapping("/api/v5/repos/{owner}/{repo}/issues")
    ResponseEntity<String> issues(@Parameter(name = "access_token", description = "用户授权码") @RequestParam(name = "access_token") String accessToken,
                                  @Parameter(name = "owner", description = "仓库所属空间地址") @PathVariable(name = "owner") String owner,
                                  @Parameter(name = "repo", description = "仓库路径") @PathVariable(name = "repo") String repo);

}
