package com.gklx.mopga.admin.ai.agent;

import com.zaxxer.hikari.HikariDataSource;
import io.agentscope.core.model.DashScopeChatModel;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.skill.repository.mysql.MysqlSkillRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {


    @Bean
    public OpenAIChatModel openAIChatModel() {
        return OpenAIChatModel.builder()
                .apiKey("sk-6c187d7368924e54a7d4cf76fe1430f0")
                .modelName("deepseek-v4-flash")
                .baseUrl("https://api.deepseek.com")
                .build();
    }

    @Bean
    public DashScopeChatModel dashScopeChatModel() {
        return DashScopeChatModel.builder()
                .apiKey("sk-bc047ae27a7c4b478f049886da1c15f0")
                .modelName("deepseek-v4-flash")
                .build();
    }


    @Bean("mysqlSkillRepository")
    public MysqlSkillRepository mysqlSkillRepository() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://49.232.58.129:3306/mopga-gen");
        ds.setUsername("root");
        ds.setPassword("Qaz@wsx");
        return new MysqlSkillRepository(ds, true, true);
    }

//    @Bean(name = "qwenChatModel")
//    public OpenAIChatModel qwenChatModel() {
//        return OpenAIChatModel.builder()
//                .apiKey("sk-6c187d7368924e54a7d4cf76fe1430f0")
//                .modelName("Qwen3-Coder-Next")
//                .baseUrl("http://10.227.224.9:8000")
//                .build();
//    }
}
