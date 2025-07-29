package com.github.aaric.sads;

import cn.hutool.core.io.IoUtil;
import com.github.aaric.sads.config.HostProperties;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.charset.Charset;

/**
 * SshClientTests
 *
 * @author Aaric
 * @version 0.13.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SshClientTests {

    private final HostProperties hostProperties;

    @Disabled
    @Test
    public void testSsh() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(hostProperties.getUsername(), hostProperties.getIntranetIp(), hostProperties.getSshPort());
        session.setPassword(hostProperties.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("pwd");
        channel.connect();

        String output = IoUtil.read(channel.getInputStream(), Charset.defaultCharset());
        log.info("output: {}", output);

        channel.disconnect();
        session.disconnect();
    }

    @Disabled
    @Test
    public void testSftp() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(hostProperties.getUsername(), hostProperties.getIntranetIp(), hostProperties.getSshPort());
        session.setPassword(hostProperties.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();

        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
        channel.put(desktopPath + "mytest/tmp.md", "/home/devops/tmp.md");
        channel.get("/home/devops/tmp.md", desktopPath + "mytest/tmp2.md");

        channel.disconnect();
        session.disconnect();
    }
}
