package com.github.aaric.sagc.command;

import cn.hutool.core.io.FileUtil;
import com.github.aaric.sagc.config.GiteeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

/**
 * GitToolTests
 *
 * @author Aaric
 * @version 0.7.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GitToolTests {

    private final GiteeProperties giteeProperties;
    private final File projectDir = new File("embedded-project/demo");

    @Test
    public void setUp() {
        String projectDirPath = projectDir.getAbsolutePath();
        System.out.printf("WorkDir: %s\n", projectDirPath);
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }

//        String command = "cmd.exe /c git --version";
//        String output = RuntimeUtil.execForStr(command.split(" "));
//        System.out.println(output);
    }

    @Test
    public void testGitClone() throws Exception {
        if (FileUtil.isEmpty(projectDir)) {
            Git.cloneRepository()
                    .setURI(giteeProperties.getEndpointUrl() + "/aaricee/test-project.git")
                    .setDirectory(projectDir)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(
                            giteeProperties.getTestOwner(), giteeProperties.getAccessToken()))
                    .call();
        }
    }

    @Test
    public void testGitPull() throws Exception {
        if (!FileUtil.isEmpty(projectDir)) {
            Git git = Git.open(projectDir);
            git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(
                            giteeProperties.getTestOwner(), giteeProperties.getAccessToken()))
                    .call();
        }
    }

    @Deprecated
    @Test
    public void testGitPush() throws Exception {
        if (!FileUtil.isEmpty(projectDir)) {
            Git git = Git.open(projectDir);
            git.add().addFilepattern(".").call();
            git.commit().setMessage("feat: init java project").call();
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(
                            giteeProperties.getTestOwner(), giteeProperties.getAccessToken()))
                    .call();
        }
    }
}
