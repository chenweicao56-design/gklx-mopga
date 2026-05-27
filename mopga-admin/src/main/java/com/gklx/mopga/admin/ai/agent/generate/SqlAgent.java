package com.gklx.mopga.admin.ai.agent.generate;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.ai.util.FreemarkerUtil;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;
import com.gklx.mopga.admin.module.generate.service.DatabaseService;
import com.gklx.mopga.admin.util.PaddleOcrClient;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import io.agentscope.core.message.ContentBlock;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.MsgRole;
import io.agentscope.core.message.TextBlock;
import io.agentscope.core.model.ChatResponse;
import io.agentscope.core.model.GenerateOptions;
import io.agentscope.core.model.OpenAIChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
    private OpenAIChatModel openAIChatModel;

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
            GenerateOptions options = GenerateOptions.builder()
                    .stream(true)  // 启用流式输出
                    .build();
            String systemPrompt = FreemarkerUtil.render("generate/sql-create-system.md", systemParams);

            List<Msg> messages = List.of(
                    Msg.builder()
                            .role(MsgRole.SYSTEM)
                            .textContent(systemPrompt)
                            .build(), Msg.builder()
                            .role(MsgRole.USER)
                            .textContent(agentContext.getQuery())
                            .build()
            );
            Flux<ChatResponse> stream = openAIChatModel.stream(messages, List.of(), options);

            stream.subscribe(chunk -> {
                List<ContentBlock> content = chunk.getContent();
                if (CollectionUtil.isNotEmpty(content)) {
                    for (ContentBlock contentBlock : content) {
                        if (contentBlock instanceof TextBlock) {
                            handler.onAnswer(com.gklx.mopga.admin.ai.core.ChatResponse.builder().content(((TextBlock) contentBlock).getText()).agentAlias(AGENT_ALIAS).build());
                        }
                    }
                }
            }, handler::onError, () -> {
                handler.onComplete(com.gklx.mopga.admin.ai.core.ChatResponse.builder().agentAlias(AGENT_ALIAS).build());
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
