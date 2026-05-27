package com.gklx.mopga.admin.ai.agent.parse;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.ai.util.FreemarkerUtil;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.domain.entity.MarkdownEntity;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.ai.tool.MinerUParseTool;
import com.gklx.mopga.admin.util.AgentUtil;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.Event;
import io.agentscope.core.agent.EventType;
import io.agentscope.core.agent.StreamOptions;
import io.agentscope.core.memory.InMemoryMemory;
import io.agentscope.core.message.ContentBlock;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.TextBlock;
import io.agentscope.core.message.ThinkingBlock;
import io.agentscope.core.model.DashScopeChatModel;
import io.agentscope.core.tool.Toolkit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service("minerUParseAgent")
public class MinerUParseAgent extends BaseAgentService {


    //    @Resource
//    private OpenAIChatModel openAIChatModel;
    @Resource
    private DashScopeChatModel dashScopeChatModel;

    @Resource
    private FileService fileService;

    @Override
    public void run(AgentContext agentContext) {
        Toolkit toolkit = new Toolkit();
        MinerUParseTool minerUParseTool = new MinerUParseTool();
        toolkit.registerTool(minerUParseTool);
        ChatHandler handler = agentContext.getChatHandler();
        JSONObject data = agentContext.getData();

        List<String> files = agentContext.getFiles();
        if (CollectionUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.size(); i++) {
                ResponseDTO<FileDownloadVO> download = fileService.getDownloadFile(files.get(0), "");
                if (download.getOk()) {
                    FileDownloadVO fileDownloadVO = download.getData();
                    String fileContent = new String(fileDownloadVO.getData(), StandardCharsets.UTF_8);
                    data.set("data", fileContent);
                }
            }
        }
        String originalData = data.getStr("data");
        if (StrUtil.isEmpty(originalData)) {
            handler.onError(new RuntimeException("数据格式不正确"));
            return;
        }

        List<MarkdownEntity> list = JSONUtil.toList(originalData, MarkdownEntity.class);
        String conversationId = agentContext.getConversationId();
        minerUParseTool.getOriginalData().put(conversationId, list);
        try {
            ReActAgent agent = ReActAgent.builder()
                    .name(agentContext.getConversationId())
                    .sysPrompt(FreemarkerUtil.render("dataset/minerUParseAgent-system.md", new HashMap<>()))
                    .toolkit(toolkit)
                    .model(dashScopeChatModel)
                    .memory(new InMemoryMemory())
                    .maxIters(100)
                    .build();
            Flux<Event> stream = agent.stream(agentContext.defaultUserMsg(), AgentUtil.getCommonStreamOptions());
            agentContext.subscribe(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
