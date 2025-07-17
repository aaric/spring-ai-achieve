package com.github.aaric.sagc.util;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * 终端工具类
 *
 * @author Aaric
 * @version 0.10.0-SNAPSHOT
 */
@Slf4j
public class TerminalUtil {

    public static String execute(String workDir, String command) {
        String output = "";
        File workDirFile = new File(workDir);
        log.info("WorkDir: {}", workDirFile.getAbsolutePath());
        if (workDirFile.exists()) {
            String osName = System.getProperty("os.name");
            if (StringUtils.hasText(osName) && osName.toLowerCase().startsWith("win")) {
                command = "cmd.exe /c " + command;
            }
            log.info("Command: {}", command);
            Process process = RuntimeUtil.exec(null, workDirFile, command.split(" "));
            output = RuntimeUtil.getResult(process);
            output += RuntimeUtil.getErrorResult(process);
        }
        return output;
    }
}
