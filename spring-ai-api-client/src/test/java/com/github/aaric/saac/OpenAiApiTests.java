package com.github.aaric.saac;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

/**
 * OpenAiApiTests
 *
 * @author Aaric
 * @version 0.1.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OpenAiApiTests {

    private final ChatClient chatClient;

    @Test
    public void testPromptText() {
//        System.err.println(chatClient.prompt().user("今天武汉天气咋样？").call().content());
//        System.err.println(chatClient.prompt().user("根据武汉的天气情况，请给岀行建议？").call().content());
//        System.err.println(chatClient.prompt().user("根据今天武汉的天气情况，请给岀我的岀行建议？").call().content());
        System.err.println(chatClient.prompt().user("今天武汉是否适合外出活动？").call().content());
    }

    @Test
    public void testGenTankVoltSQL() {
        String data = """
                TODO
                """;
        int typeId = 8;
        System.err.printf("DELETE FROM t_par*t*ner_res*o*urce WHERE par*t*ner_type_id = %d;\n\n", typeId);
        System.err.println("INSERT INTO t_par*t*ner_res*o*urce (pr_id, par*t*ner_type_id, res*o*urce_id) VALUES");

        List<String> idList = Arrays.stream(data.split("\n"))
                .filter(s -> !s.isEmpty())
                .toList();
        for (int i = 0; i < idList.size(); i++) {
            System.err.printf("(%d, %d, %s),\n", (2000 + typeId * 100 + i), typeId, idList.get(i));
        }
    }
}
