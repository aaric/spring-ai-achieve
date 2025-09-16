package com.github.aaric.salg.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * OpinionJudgeService
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
public interface OpinionJudgeService {

    @SystemMessage("你是一个舆情识别专家，你的任务是判断用户输入是正面情绪还是负面情绪，可能还要考虑天气情况，输出\"正面\"或者\"负面\"，不要输出其他解析内容。")
    @UserMessage("用户提问：{{question}}")
    String chat(@V("question") String question);
}
