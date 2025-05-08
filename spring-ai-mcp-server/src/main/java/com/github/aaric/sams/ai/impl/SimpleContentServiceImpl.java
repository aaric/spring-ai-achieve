package com.github.aaric.sams.ai.impl;

import com.github.aaric.sams.ai.SimpleContentService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * MessageServiceImpl
 *
 * @author Aaric
 * @version 0.2.0-SNAPSHOT
 */
@Service
public class SimpleContentServiceImpl implements SimpleContentService {

    @Override
    @Tool(description = "推荐技术内容")
    public String recommendContent() {
        return "推荐给你一些内容，写的很好！";
    }

    @Override
    @Tool(description = "最好的技术内容")
    public String bestContext() {
        return "最好的技术内容，值得你瞧瞧，写的很棒！";
    }
}
