package com.github.aaric.sagc.feign.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 问题状态
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Getter
@AllArgsConstructor
public enum IssueStateEnum {

    OPEN("open", "开启的"),
    PROGRESSING("progressing", "进行中"),
    CLOSED("closed", "关闭的"),
    ALL("all", "全部的");

    @JsonValue
    private final String code;
    private final String desc;

    @JsonCreator
    public static IssueStateEnum fromValue(String value) {
        for (IssueStateEnum state : values()) {
            if (state.getCode().equalsIgnoreCase(value) ||
                    state.name().equalsIgnoreCase(value)) {
                return state;
            }
        }
        return null;
    }
}
