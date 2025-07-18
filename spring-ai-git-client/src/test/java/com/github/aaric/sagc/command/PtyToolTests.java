package com.github.aaric.sagc.command;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * PtyToolTests
 *
 * @author Aaric
 * @version 0.10.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class PtyToolTests {

    @Test
    public void testJavaVersionWithApacheExec() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream()) {
            CommandLine commandLine = CommandLine.parse("java -version");
            DefaultExecutor executor = DefaultExecutor.builder().get();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorOutputStream);
            executor.setStreamHandler(streamHandler);
            ExecuteWatchdog watchdog = ExecuteWatchdog.builder()
                    .setTimeout(Duration.ofSeconds(60)).get();
            executor.setWatchdog(watchdog);
            int exitValue = executor.execute(commandLine);
            System.out.println("exitValue: " + exitValue);
            System.out.println(outputStream);
            System.out.println(errorOutputStream);
        } catch (Exception e) {
            log.error("execute error", e);
        }
    }

    @Test
    public void testJavaVersionWithApacheExecAsync() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream()) {
            CommandLine commandLine = CommandLine.parse("java -version");
            DefaultExecutor executor = DefaultExecutor.builder().get();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorOutputStream);
            executor.setStreamHandler(streamHandler);

            ExecuteResultHandler resultHandler = new ExecuteResultHandler() {
                @Override
                public void onProcessComplete(int exitValue) {
                    System.out.println("exitValue: " + exitValue);
                    System.out.println(outputStream);
                    System.out.println(errorOutputStream);
                }

                @Override
                public void onProcessFailed(ExecuteException e) {
                    System.out.println(errorOutputStream);
                }
            };

            executor.execute(commandLine, resultHandler);
            Thread.currentThread().join();
        } catch (Exception e) {
            log.error("execute error", e);
        }
    }

    @Test
    public void testJavaVersionWithJetbrainsPty() throws Exception {
        Map<String, String> env = new HashMap<>();
        env.put("TERM", "xterm");
        PtyProcess process = new PtyProcessBuilder()
                .setCommand("java -version".split(" "))
                .setEnvironment(env)
                .start();

        InputStream inputStream = process.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            System.out.print(new String(buffer, 0, bytesRead));
        }

        int exitValue = process.waitFor();
        System.out.println("exitValue: " + exitValue);
    }
}
