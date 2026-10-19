package com.gklx.mopga.admin.ai.agent.generate;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;
import com.gklx.mopga.admin.module.generate.service.DatabaseService;
import com.gklx.mopga.admin.util.FreemarkerUtil;
import com.gklx.mopga.admin.util.PaddleOcrClient;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("sqlGenerateAgentService")
public class SqlAgent extends BaseAgentService {

    @Resource
    private FileService fileService;
    @Resource
    private DatabaseService databaseService;

    @Resource
    private ChatModel chatModel;

    @Resource
    private PaddleOcrClient paddleOcrClient;

    public static final String AGENT_ALIAS = "sqlGenerateAgentService";


    @Override
    public void run(AgentContext agentContext) {
        ChatHandler handler = agentContext.getChatHandler();
        JSONObject data = agentContext.getData();
        Long databaseId = data.getLong("databaseId");
        DatabaseVo databaseVo = databaseService.get(databaseId);
        String databaseType = databaseVo.getDatabaseType();
        List<String> files = agentContext.getFiles();
        StringBuilder designDrawing = new StringBuilder();
        if (CollectionUtil.isNotEmpty(files)) {
            for (int i = 0; i < files.size(); i++) {
                ResponseDTO<FileDownloadVO> download = fileService.getDownloadFile(files.get(0), "");
                if (download.getOk()) {
                    FileDownloadVO fileDownloadVO = download.getData();
                    String run = paddleOcrClient.run(fileDownloadVO);
                    designDrawing.append(run);
                }
            }
        }

        try {
            Map<String, Object> systemParams = MapUtil.<String, Object>builder()
                    .put("dbEngine", databaseType)
                    .put("currentTableStructure", JSONUtil.toJsonStr(data))
                    .put("commonColumnType", "")
                    .put("designDrawing", designDrawing.toString())
                    .build();
            String systemPrompt = FreemarkerUtil.render("generate/sql-create-system.txt", systemParams);

            List<Message> messages = new ArrayList<>();
            messages.add(new SystemMessage(systemPrompt));
            messages.add(new UserMessage(agentContext.getQuery()));

            Prompt prompt = new Prompt(messages);
            Flux<String> stream = chatModel.stream(prompt)
                    .map(r -> r.getResult().getOutput().getText());
            agentContext.subscribe(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
