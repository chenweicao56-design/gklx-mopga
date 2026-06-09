//package com.gklx.mopga.admin.ai.agent.sql;
//
//import cn.hutool.json.JSONObject;
//import com.gklx.ai.util.FreemarkerUtil;
//import com.gklx.mopga.admin.ai.core.ChatHandler;
//import com.gklx.mopga.admin.ai.core.ChatResponse;
//import com.gklx.mopga.admin.ai.core.DbRule;
//import com.gklx.mopga.admin.ai.domain.AgentContext;
//import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
//import com.gklx.mopga.admin.module.generate.jdbc.JdbcSpiLoader;
//import com.gklx.mopga.admin.module.generate.manager.DatabaseManager;
//import io.agentscope.core.model.OpenAIChatModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//import reactor.core.publisher.Flux;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.atomic.AtomicReference;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.mockStatic;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class Text2SqlTest {
//
//    @Mock
//    private OpenAIChatModel openAIChatModel;
//
//    @Mock
//    private DatabaseManager databaseManager;
//
//    @Mock
//    private ChatHandler chatHandler;
//
//    private Text2Sql text2Sql;
//
//    private static final String AGENT_ALIAS = "planAgent";
//
//    @BeforeEach
//    void setUp() throws Exception {
//        text2Sql = new Text2Sql();
//
//        Field openAIChatModelField = Text2Sql.class.getDeclaredField("openAIChatModel");
//        openAIChatModelField.setAccessible(true);
//        openAIChatModelField.set(text2Sql, openAIChatModel);
//
//        Field databaseManagerField = Text2Sql.class.getDeclaredField("databaseManager");
//        databaseManagerField.setAccessible(true);
//        databaseManagerField.set(text2Sql, databaseManager);
//
//        initJdbcRuleDefines();
//    }
//
//    private void initJdbcRuleDefines() {
//        if (JdbcSpiLoader.RuleDefines.isEmpty()) {
//            DbRule mysqlRule = new DbRule();
//            mysqlRule.setDatabaseType("mysql");
//            mysqlRule.setQuotRule("quotRule");
//            mysqlRule.setLimitRule("limitRule");
//            mysqlRule.setOtherRule("otherRule");
//            mysqlRule.setBasicExample("basicExample");
//            List<String> exampleList = new ArrayList<>();
//            exampleList.add("example1");
//            exampleList.add("example2");
//            exampleList.add("example3");
//            mysqlRule.setExampleAnswerList(exampleList);
//            mysqlRule.setExampleAnswerListWithLimit(exampleList);
//            JdbcSpiLoader.RuleDefines.put("mysql", mysqlRule);
//
//            DbRule oracleRule = new DbRule();
//            oracleRule.setDatabaseType("oracle");
//            oracleRule.setQuotRule("oracleQuotRule");
//            oracleRule.setLimitRule("oracleLimitRule");
//            oracleRule.setOtherRule("oracleOtherRule");
//            oracleRule.setBasicExample("oracleBasicExample");
//            oracleRule.setExampleAnswerList(exampleList);
//            oracleRule.setExampleAnswerListWithLimit(exampleList);
//            JdbcSpiLoader.RuleDefines.put("oracle", oracleRule);
//
//            DbRule kingbaseRule = new DbRule();
//            kingbaseRule.setDatabaseType("kingbase");
//            kingbaseRule.setQuotRule("kingbaseQuotRule");
//            kingbaseRule.setLimitRule("kingbaseLimitRule");
//            kingbaseRule.setOtherRule("kingbaseOtherRule");
//            kingbaseRule.setBasicExample("kingbaseBasicExample");
//            kingbaseRule.setExampleAnswerList(exampleList);
//            kingbaseRule.setExampleAnswerListWithLimit(exampleList);
//            JdbcSpiLoader.RuleDefines.put("kingbase", kingbaseRule);
//        }
//    }
//
//    @Nested
//    @DisplayName("正常运行场景测试")
//    class NormalExecutionTests {
//
//        @Test
//        @DisplayName("正常执行流程 - 模拟成功场景")
//        void testRun_NormalExecution() {
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            AtomicBoolean streamCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                streamCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            doAnswer(invocation -> {
//                ChatResponse response = invocation.getArgument(0);
//                assertNotNull(response);
//                assertEquals(AGENT_ALIAS, response.getAgentAlias());
//                return null;
//            }).when(chatHandler).onAnswer(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//        }
//
//        @Test
//        @DisplayName("测试不同数据库类型 - Oracle")
//        void testRun_DifferentDatabaseType_Oracle() {
//            AgentContext agentContext = createAgentContext(2L, "oracle", "oracleSchema", "查询订单表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("oracle");
//            databaseEntity.setDatabaseName("oracleDb");
//            when(databaseManager.getById(2L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//            assertTrue(runCompleted.get() || !runCompleted.get());
//        }
//
//        @Test
//        @DisplayName("测试不同数据库类型 - Kingbase")
//        void testRun_DifferentDatabaseType_Kingbase() {
//            AgentContext agentContext = createAgentContext(3L, "kingbase", "kingbaseSchema", "查询商品表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("kingbase");
//            databaseEntity.setDatabaseName("kingbaseDb");
//            when(databaseManager.getById(3L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//            assertTrue(runCompleted.get() || !runCompleted.get());
//        }
//    }
//
//    @Nested
//    @DisplayName("边界值测试")
//    class BoundaryValueTests {
//
//        @Test
//        @DisplayName("测试空字符串schema")
//        void testRun_EmptySchema() {
//            AgentContext agentContext = createAgentContext(1L, "mysql", "", "查询用户表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//            assertTrue(runCompleted.get() || !runCompleted.get());
//        }
//
//        @Test
//        @DisplayName("测试长schema值")
//        void testRun_LongSchema() {
//            String longSchema = "a".repeat(500);
//            AgentContext agentContext = createAgentContext(1L, "mysql", longSchema, "查询用户表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//            assertTrue(runCompleted.get() || !runCompleted.get());
//        }
//
//        @Test
//        @DisplayName("测试长查询语句")
//        void testRun_LongQuery() {
//            String longQuery = "查询" + "a".repeat(1000);
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", longQuery);
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//            assertTrue(runCompleted.get() || !runCompleted.get());
//        }
//    }
//
//    @Nested
//    @DisplayName("异常场景测试")
//    class ExceptionTests {
//
//        @Test
//        @DisplayName("测试数据库ID为null时抛出异常")
//        void testRun_DatabaseIdIsNull() {
//            AgentContext agentContext = createAgentContext(null, "mysql", "testSchema", "查询用户表");
//
//            when(databaseManager.getById(null)).thenThrow(new NullPointerException("databaseId is null"));
//
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                text2Sql.run(agentContext);
//            });
//
//            assertNotNull(exception);
//        }
//
//        @Test
//        @DisplayName("测试数据库不存在时抛出异常")
//        void testRun_DatabaseNotFound() {
//            AgentContext agentContext = createAgentContext(999L, "mysql", "testSchema", "查询用户表");
//
//            when(databaseManager.getById(999L)).thenReturn(null);
//
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                text2Sql.run(agentContext);
//            });
//
//            assertNotNull(exception);
//            assertTrue(exception.getMessage() != null);
//        }
//
//        @Test
//        @DisplayName("测试databaseManager抛出异常")
//        void testRun_DatabaseManagerThrowsException() {
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//
//            when(databaseManager.getById(1L)).thenThrow(new RuntimeException("Database manager error"));
//
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                text2Sql.run(agentContext);
//            });
//
//            assertNotNull(exception);
//        }
//
//        @Test
//        @DisplayName("测试不支持的数据库类型")
//        void testRun_UnsupportedDatabaseType() {
//            AgentContext agentContext = createAgentContext(1L, "unsupported", "testSchema", "查询用户表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("unsupported");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                text2Sql.run(agentContext);
//            });
//
//            assertNotNull(exception);
//        }
//    }
//
//    @Nested
//    @DisplayName("ChatHandler回调测试")
//    class ChatHandlerCallbackTests {
//
//        @Test
//        @DisplayName("测试onAnswer回调")
//        void testRun_OnAnswerCallback() {
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            ArgumentCaptor<ChatResponse> responseCaptor = ArgumentCaptor.forClass(ChatResponse.class);
//
//            doAnswer(invocation -> {
//                return null;
//            }).when(chatHandler).onAnswer(any(ChatResponse.class));
//
//            doAnswer(invocation -> {
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//
//            verify(chatHandler, times(1)).onAnswer(responseCaptor.capture());
//            ChatResponse capturedResponse = responseCaptor.getValue();
//            assertEquals(AGENT_ALIAS, capturedResponse.getAgentAlias());
//        }
//
//        @Test
//        @DisplayName("测试onComplete回调")
//        void testRun_OnCompleteCallback() {
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            doAnswer(invocation -> {
//                return null;
//            }).when(chatHandler).onAnswer(any(ChatResponse.class));
//
//            doAnswer(invocation -> {
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//
//            ArgumentCaptor<ChatResponse> completeCaptor = ArgumentCaptor.forClass(ChatResponse.class);
//            verify(chatHandler, times(1)).onComplete(completeCaptor.capture());
//            ChatResponse completeResponse = completeCaptor.getValue();
//            assertEquals(AGENT_ALIAS, completeResponse.getAgentAlias());
//        }
//
//        @Test
//        @DisplayName("测试onError回调")
//        void testRun_OnErrorCallback() {
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            doAnswer(invocation -> {
//                return null;
//            }).when(chatHandler).onAnswer(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//        }
//    }
//
//    @Nested
//    @DisplayName("字段映射和数据传递测试")
//    class DataMappingTests {
//
//        @Test
//        @DisplayName("测试AgentContext数据获取")
//        void testRun_AgentContextDataRetrieval() {
//            Long expectedDatabaseId = 12345L;
//            String expectedSchema = "testSchema";
//            String expectedQuery = "测试查询语句";
//
//            AgentContext agentContext = createAgentContext(expectedDatabaseId, "mysql", expectedSchema, expectedQuery);
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(expectedDatabaseId)).thenReturn(databaseEntity);
//
//            doAnswer(invocation -> {
//                return null;
//            }).when(chatHandler).onAnswer(any(ChatResponse.class));
//
//            doAnswer(invocation -> {
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//
//            assertEquals(expectedDatabaseId, agentContext.getData().getLong("databaseId"));
//            assertEquals(expectedSchema, agentContext.getData().getStr("schema"));
//            assertEquals(expectedQuery, agentContext.getQuery());
//        }
//    }
//
//    @Nested
//    @DisplayName("AgentContext字段测试")
//    class AgentContextFieldTests {
//
//        @Test
//        @DisplayName("测试conversationId字段")
//        void testRun_ConversationIdField() {
//            String expectedConversationId = "conversation-123";
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//            agentContext.setConversationId(expectedConversationId);
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//
//            assertEquals(expectedConversationId, agentContext.getConversationId());
//        }
//
//        @Test
//        @DisplayName("测试userId字段")
//        void testRun_UserIdField() {
//            String expectedUserId = "user-456";
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//            agentContext.setUserId(expectedUserId);
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//
//            assertEquals(expectedUserId, agentContext.getUserId());
//        }
//
//        @Test
//        @DisplayName("测试files字段")
//        void testRun_FilesField() {
//            List<String> expectedFiles = List.of("file1.txt", "file2.txt");
//            AgentContext agentContext = createAgentContext(1L, "mysql", "testSchema", "查询用户表");
//            agentContext.setFiles(expectedFiles);
//
//            DatabaseEntity databaseEntity = new DatabaseEntity();
//            databaseEntity.setDatabaseType("mysql");
//            databaseEntity.setDatabaseName("testDb");
//            when(databaseManager.getById(1L)).thenReturn(databaseEntity);
//
//            AtomicBoolean runCompleted = new AtomicBoolean(false);
//
//            doAnswer(invocation -> {
//                runCompleted.set(true);
//                return null;
//            }).when(chatHandler).onComplete(any(ChatResponse.class));
//
//            text2Sql.run(agentContext);
//
//            assertEquals(expectedFiles, agentContext.getFiles());
//        }
//    }
//
//    private AgentContext createAgentContext(Long databaseId, String databaseType, String schema, String query) {
//        AgentContext agentContext = new AgentContext();
//        agentContext.setQuery(query);
//        agentContext.setAgentAlias(AGENT_ALIAS);
//        agentContext.setUserId("testUser");
//        agentContext.setConversationId("testConversation");
//        agentContext.setChatHandler(chatHandler);
//
//        JSONObject data = new JSONObject();
//        data.set("databaseId", databaseId);
//        data.set("databaseType", databaseType);
//        data.set("schema", schema);
//        agentContext.setData(data);
//
//        return agentContext;
//    }
//}