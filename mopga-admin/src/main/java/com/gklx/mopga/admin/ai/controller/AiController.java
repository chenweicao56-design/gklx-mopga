package com.gklx.mopga.admin.ai.controller;

import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.ChatService;
import com.gklx.mopga.admin.util.PaddleOcrClient;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AiController {

    @Resource
    private FileService fileService;
    @Resource
    private PaddleOcrClient paddleOcrClient;

    @Resource
    private ChatService chatService;

    @PostMapping("/chat")
    public Flux<ChatResponse> chat(@RequestBody AgentContext context) {
        return chatService.chat(context);
    }


    @GetMapping("/test")
    public Flux<String> test() {
        ResponseDTO<FileDownloadVO> downloadFile = fileService.getDownloadFile("", "");
        String run = paddleOcrClient.run(downloadFile.getData());
        System.out.println(run);
        return Flux.just(run);
    }

}
