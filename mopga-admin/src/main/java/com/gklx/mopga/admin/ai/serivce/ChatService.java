package com.gklx.mopga.admin.ai.serivce;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.ai.agent.TestAgent;
import com.gklx.mopga.admin.ai.domain.form.ChatQueryForm;
import com.gklx.mopga.admin.ai.domain.vo.ChatVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author GKLX
 * @description: AI服务
 * @create 2021-02-25 16:00
 */
@Service
public class ChatService {

    @Resource
    private FrontCustomComponentService frontCustomComponentService;
    @Resource
    private TestAgent testAgent;


    public Flux<String> chat(ChatQueryForm chatQueryForm) {
        String agentNo = chatQueryForm.getAgentNo();
        if (StrUtil.isNotEmpty(agentNo)) {
            switch (agentNo) {
                case "testAgent":
                    return  Flux.just("你好，我不明白你的意思，请问有什么可以帮助您?");
                default:
                    return Flux.just("你好，我不明白你的意思，请问有什么可以帮助您?");

            }
        } else {
            return frontCustomComponentService.chat(chatQueryForm.getQuestion()).map(e -> {
                ChatVo chatVo = new ChatVo();
                chatVo.setContent(e);
                chatVo.setType("ai");
                return JSONUtil.toJsonStr(chatVo);
            });
        }
    }


}
