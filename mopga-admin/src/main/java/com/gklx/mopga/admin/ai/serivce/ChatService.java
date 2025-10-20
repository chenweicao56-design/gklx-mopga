package com.gklx.mopga.admin.ai.serivce;

import cn.hutool.json.JSONUtil;
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


    public Flux<String> chat(ChatQueryForm chatQueryForm) {

        return frontCustomComponentService.chat(chatQueryForm.getQuestion()).map(e -> {
            ChatVo chatVo = new ChatVo();
            chatVo.setContent(e);
            chatVo.setType("ai");
            return JSONUtil.toJsonStr(chatVo);
        });
    }


}
