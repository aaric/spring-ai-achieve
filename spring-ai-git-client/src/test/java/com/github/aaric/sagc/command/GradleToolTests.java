package com.github.aaric.sagc.command;

import cn.hutool.core.util.RuntimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;

/**
 * GradleToolTests
 *
 * @author Aaric
 * @version 0.7.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GradleToolTests {

    @Deprecated
    @Test
    public void testGradleBuild() throws Exception {
        String command = "cmd.exe /c gradle build";
        File projectDir = new File("embedded-project/demo");
        System.out.printf("WorkDir: %s\n", projectDir.getAbsolutePath());
        if (projectDir.exists()) {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.directory(projectDir);
            Process process = processBuilder.start();

            String line;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
                while (null != (line = reader.readLine())) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                log.info("", e);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "GBK"))) {
                while (null != (line = reader.readLine())) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                log.info("", e);
            }

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        }
    }

    @Deprecated
    @Test
    public void testGradleBuildWithHuTool() throws Exception {
        String command = "cmd.exe /c gradle clean build";
//        String output = RuntimeUtil.execForStr(command.split(" "));
//        System.err.println(output);
        File projectDir = new File("embedded-project/demo");
        System.out.printf("WorkDir: %s\n", projectDir.getAbsolutePath());
        if (projectDir.exists()) {
            Process process = RuntimeUtil.exec(null, projectDir, command.split(" "));
            String result = RuntimeUtil.getResult(process);
            System.out.println(result);
            result = RuntimeUtil.getErrorResult(process);
            System.out.println(result);
        }
    }

    @Test
    public void testGradleBuildWithApacheExec() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream()) {
            CommandLine commandLine = CommandLine.parse("java -version");
            DefaultExecutor executor = DefaultExecutor.builder().get();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorOutputStream);
            executor.setStreamHandler(streamHandler);
            int code = executor.execute(commandLine);
            System.out.println("code: " + code);
            System.out.println(outputStream.toString());
            System.out.println(errorOutputStream.toString());
        } catch (Exception e) {
            log.error("execute error", e);
        }
    }
}
