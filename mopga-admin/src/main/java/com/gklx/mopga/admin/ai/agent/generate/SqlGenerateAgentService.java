package com.gklx.mopga.admin.ai.agent.generate;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.ai.core.ChatHandler;
import com.gklx.mopga.admin.ai.core.ChatResponse;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import com.gklx.mopga.admin.ai.serivce.BaseAgentService;
import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;
import com.gklx.mopga.admin.module.generate.service.DatabaseService;
import com.gklx.mopga.admin.util.PaddleOcrClient;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.module.support.file.domain.vo.FileDownloadVO;
import com.gklx.mopga.base.module.support.file.service.FileService;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("sqlGenerateAgentService")
public class SqlGenerateAgentService extends BaseAgentService {

    public static final String AGENT_ALIAS = "sqlGenerateAgentService";

    @Resource
    private SqlGenerateAgent sqlGenerateAgent;
    @Resource
    private DatabaseService databaseService;
    @Resource
    private FileService fileService;
    @Resource
    private PaddleOcrClient paddleOcrClient;

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
        TokenStream run = sqlGenerateAgent.run(agentContext.getQuery(), databaseType, JSONUtil.toJsonStr(data), "", designDrawing.toString());
        run.onPartialResponse(message -> {
                    handler.onAnswer(com.gklx.mopga.admin.ai.core.ChatResponse.builder().content(message).agentAlias(AGENT_ALIAS).build());
                }).onCompleteResponse(s -> {
                    handler.onComplete(ChatResponse.builder().agentAlias(AGENT_ALIAS).build());
                }).onError(handler::onError)
                .onRetrieved(s -> {
                    s.forEach(System.out::println);
                }).start();
    }
}
