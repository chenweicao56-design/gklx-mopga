package com.gklx.mopga.admin.ai.controller;

import cn.hutool.core.map.MapUtil;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.core.DbRule;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.ChatService;
import com.gklx.mopga.admin.module.generate.jdbc.JdbcSpiLoader;
import com.gklx.mopga.admin.util.FreemarkerUtil;
import com.gklx.mopga.admin.util.PaddleOcrClient;
import com.gklx.mopga.base.common.annoation.NoNeedLogin;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class AiController {

    @Resource
    private FileService fileService;
    @Resource
    private PaddleOcrClient paddleOcrClient;

    @Resource
    private ChatService chatService;

    @Resource
    private ChatModel chatModel;

    @PostMapping("/chat")
    public Flux<ChatResponse> chat(@RequestBody AgentContext context) {
        return chatService.chat(context);
    }


    @GetMapping("/test")
    public Flux<String> test() throws Exception {
        DbRule dbRule = JdbcSpiLoader.RuleDefines.get("3306");
        Map<String, Object> systemParams = MapUtil.<String, Object>builder()
                .put("engine", "3306")
                .put("schema", "")
                .put("quotRule", dbRule.getQuotRule())
                .put("limitRule", dbRule.getLimitRule())
                .put("otherRule", dbRule.getOtherRule())
                .put("basicExample", dbRule.getBasicExample())
                .put("exampleAnswer1", dbRule.getExampleAnswerListWithLimit().get(0))
                .put("exampleAnswer2", dbRule.getExampleAnswerListWithLimit().get(0))
                .put("exampleAnswer3", dbRule.getExampleAnswerListWithLimit().get(0))
                .build();
        String systemPrompt = FreemarkerUtil.render("sql/system.md", systemParams);
        System.out.println(systemPrompt);

        ResponseDTO<FileDownloadVO> downloadFile = fileService.getDownloadFile("", "");
        String run = paddleOcrClient.run(downloadFile.getData());
        System.out.println(run);
        return Flux.just(run);
    }

    @GetMapping("/test2")
    @NoNeedLogin
    public Flux<String> test2(@RequestParam("query") String query) throws Exception {

        List<Message> messages = List.of(
                new SystemMessage("你是一个有帮助的助手。"),
                new UserMessage(query)
        );
        Prompt prompt = new Prompt(messages);
        return chatModel.stream(prompt)
                .map(r -> r.getResult().getOutput().getText());
    }

}
