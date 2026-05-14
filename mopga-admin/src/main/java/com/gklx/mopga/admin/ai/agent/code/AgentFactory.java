package com.gklx.mopga.admin.ai.agent.code;

import com.gklx.ai.util.FreemarkerUtil;
import com.gklx.mopga.admin.ai.agent.hook.LogHook;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.memory.InMemoryMemory;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.skill.AgentSkill;
import io.agentscope.core.skill.SkillBox;
import io.agentscope.core.skill.repository.FileSystemSkillRepository;
import io.agentscope.core.tool.Toolkit;
import io.agentscope.core.tool.coding.ShellCommandTool;
import io.agentscope.core.tool.file.ReadFileTool;
import io.agentscope.core.tool.file.WriteFileTool;
import io.agentscope.core.tool.mcp.McpClientBuilder;
import io.agentscope.core.tool.mcp.McpClientWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AgentFactory {

    private Map<String, ReActAgent> agents = new HashMap<>();

    @Resource
    private OpenAIChatModel openAIChatModel;

    public ReActAgent getAgent(String session) {
        ReActAgent reActAgent = agents.get(session);
        try {
            if (reActAgent == null) {
                McpClientWrapper client = McpClientBuilder.create("http-mcp")
                        .streamableHttpTransport("http://127.0.0.1:1024/mcp")
                        .buildAsync()
                        .block();
                Toolkit toolkit = new Toolkit();
                toolkit.registerMcpClient(client).block(Duration.ofSeconds(10));
                toolkit.registerTool(new ReadFileTool());
                toolkit.registerTool(new WriteFileTool());
                toolkit.registerTool(new ShellCommandTool());

                SkillBox skillBox = new SkillBox(toolkit);
                AgentSkill skillCreator = loadSkillCreatorSkill();
                skillBox.registration().skill(skillCreator).apply();

                reActAgent = ReActAgent.builder()
                        .name(session)
                        .sysPrompt(FreemarkerUtil.render("code/system.md", new HashMap<>()))
                        .toolkit(toolkit)
                        .model(openAIChatModel)
                        .memory(new InMemoryMemory())
                        .hook(new LogHook())
                        .build();
                agents.put(session, reActAgent);
            }
        } catch (Exception ignored) {
            throw new RuntimeException("Failed to load agent " + session);
        }
        return reActAgent;
    }

    private static AgentSkill loadSkillCreatorSkill() {
        Path resourcesDir = resolvePath("mopga-admin/src/main/resources/skills");
        FileSystemSkillRepository repository = new FileSystemSkillRepository(resourcesDir, false);
        return repository.getSkill("skill-creator");
    }

    private static Path resolvePath(String relativePath) {
        return Paths.get(relativePath).toAbsolutePath().normalize();
    }

}
