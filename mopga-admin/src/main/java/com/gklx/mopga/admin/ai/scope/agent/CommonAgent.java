package com.gklx.mopga.admin.ai.scope.agent;

import com.gklx.ai.core.agent.SimpleTools;
import com.gklx.mopga.admin.ai.domain.AgentContext;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.skill.SkillBox;
import io.agentscope.core.tool.Toolkit;

public class CommonAgent {


    void run(AgentContext context) {

        Toolkit toolkit = new Toolkit();
        toolkit.registerTool(new SimpleTools());

        SkillBox skillBox = new SkillBox(toolkit);

// 启用所有代码执行工具(Shell、读文件、写文件)
        skillBox.codeExecution()
                .withShell()
                .withRead()
                .withWrite()
                .enable();

        OpenAIChatModel model = OpenAIChatModel.builder()
                .apiKey("sk-87c6cc6fb84440aca57f17305a6d69de")
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com")
                .build();

        ReActAgent jarvis = ReActAgent.builder()
                .name("Jarvis")
                .sysPrompt("你是一个名为 Jarvis 的助手")
                .model(model)
                .toolkit(toolkit)
                .build();
    }

}
