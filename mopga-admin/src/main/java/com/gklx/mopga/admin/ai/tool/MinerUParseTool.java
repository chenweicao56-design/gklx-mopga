package com.gklx.mopga.admin.ai.tool;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.ai.domain.entity.MarkdownEntity;
import com.gklx.mopga.admin.ai.domain.entity.MarkdownVo;
import io.agentscope.core.tool.Tool;
import io.agentscope.core.tool.ToolParam;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MinerUParseTool {


    Map<String, List<MarkdownEntity>> OriginalData = new HashMap<>();
    Map<String, List<MarkdownVo>> GenerateData = new HashMap<>();

    public Map<String, List<MarkdownEntity>> getOriginalData() {
        return OriginalData;
    }

    public Map<String, List<MarkdownVo>> getGenerateData() {
        return GenerateData;
    }


    @Tool(description = "获取当前会话要处理数据的总页数")
    public String getMarkdownDataCount(
            @ToolParam(name = "session", description = "会话ID") String session) {
        log.info("===============getMarkdownDataCount=======================");

        // 参数校验
        if (session == null || session.trim().isEmpty()) {
            return "错误：会话ID不能为空";
        }

        // 获取数据列表
        List<MarkdownEntity> markdownEntities = OriginalData.get(session);

        // 空值处理
        if (markdownEntities == null) {
            return String.format("会话 %s 不存在或暂无数据", session);
        }

        // 返回数据总量
        if (CollectionUtil.isEmpty(markdownEntities)) {
            return String.format("会话 %s 当前没有待处理的数据", session);
        }
        String pageNumber = markdownEntities.get(markdownEntities.size() - 1).getPage_number();
        return String.format("会话 %s 当前要处理的数据总页数：%s 页", session, pageNumber);
    }

    @Tool(description = "分页获取要处理数据的详细的原始数据，比如获取数据第1页至第10页的原始数据")
    public String getMarkdownData(
            @ToolParam(name = "session", description = "会话ID") String session,
            @ToolParam(name = "startPage", description = "开始页，从1开始") int startPage,
            @ToolParam(name = "endPage", description = "结束页，默认10页") int endPage) {

        log.info("===============getMarkdownData=======================");
        log.info("======session:{}, currentPage:{}, pageSize:{}", session, startPage, endPage);
        log.info("===============getMarkdownData=======================");
        // 参数校验和默认值设置
        if (session == null || session.trim().isEmpty()) {
            return "错误：会话ID不能为空";
        }

        if (startPage < 1) {
            startPage = 1;
        }

        if (endPage < startPage) {
            endPage = startPage;
        }

        // 获取数据列表
        List<MarkdownEntity> markdownEntities = OriginalData.get(session);
        if (CollectionUtil.isEmpty(markdownEntities)) {
            return "暂无数据";
        }

        int finalStartPage = startPage;
        int finalEndPage = endPage;
        String pageNumber = markdownEntities.get(markdownEntities.size() - 1).getPage_number();
        List<MarkdownEntity> filtered = markdownEntities.stream()
                .filter(entity -> {
                    try {
                        int pageNum = Integer.parseInt(entity.getPage_number());
                        return pageNum >= finalStartPage && pageNum <= finalEndPage;
                    } catch (NumberFormatException e) {
                        log.warn("无效的页码格式: {}", entity.getPage_number());
                        return false;
                    }
                })
                .toList();
        if (filtered.isEmpty()) {
            return String.format("未找到页码范围 [%d, %d] 内的数据", startPage, endPage);
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("共%s页，当前第%d页，每页%d条，总条数%d\n\n",
                pageNumber, startPage, endPage, filtered.size()));

        for (MarkdownEntity entity : filtered) {
            result.append(String.format("%s\n", entity.toString()));
        }
        return result.toString();
    }

    @Tool(description = "生成数据操作")
    public String generateData(
            @ToolParam(name = "session", description = "会话ID") String session,
            @ToolParam(name = "operateType", description = "操作类型，新增：add;删除：delete;更新：update") String type,
            @ToolParam(name = "data", description = "数据，JSON数组字符串。格式示例：[{\\\"id\\\": \\\"newId123\\\", \\\"ids\\\": [\\\"seg001\\\", \\\"seg002\\\"]}]。字段说明：id -合并的分段标识（自动生成，更新的时候根据该id更新）；ids - 待合并的原始分段ID列表，至少包含一个ID，注意只有这两个字段，不要生成其他的") String data
    ) {

        log.info("===============generateData=======================");
        log.info("=======session:{}, operateType:{}, data:{}", session, type, data);
        log.info("===============generateData=======================");

        // 参数校验
        if (session == null || session.trim().isEmpty()) {
            return "错误：会话ID不能为空";
        }
        if (type == null || type.trim().isEmpty()) {
            return "错误：操作类型不能为空";
        }
        if (data == null || data.trim().isEmpty()) {
            return "错误：数据不能为空";
        }

        // 获取会话数据
        List<MarkdownVo> markdownVos = GenerateData.get(session);
        if (CollectionUtil.isEmpty(markdownVos)) {
            markdownVos = new ArrayList<>();
        }

        try {
            List<MarkdownVo> list = JSONUtil.toList(data, MarkdownVo.class);

            if ("add".equals(type)) {
                markdownVos.addAll(list);
                // 保存更新后的数据
                GenerateData.put(session, markdownVos);
                return String.format("成功添加 %d 条数据", list.size());

            } else if ("delete".equals(type)) {
                int deletedCount = 0;
                for (MarkdownVo deleteItem : list) {
                    String deleteId = deleteItem.getId();
                    if (deleteId != null) {
                        boolean removed = markdownVos.removeIf(item -> deleteId.equals(item.getId()));
                        if (removed) deletedCount++;
                    }
                }
                GenerateData.put(session, markdownVos);
                return String.format("成功删除 %d 条数据", deletedCount);

            } else if ("update".equals(type)) {
                // 根据id更新数据
                int updatedCount = 0;
                for (MarkdownVo updateItem : list) {
                    String updateId = updateItem.getId();
                    if (updateId != null) {
                        for (int i = 0; i < markdownVos.size(); i++) {
                            if (updateId.equals(markdownVos.get(i).getId())) {
                                markdownVos.set(i, updateItem);
                                updatedCount++;
                                break;
                            }
                        }
                    }
                }
                GenerateData.put(session, markdownVos);
                return String.format("成功更新 %d 条数据", updatedCount);

            } else {
                return "错误：不支持的操作类型：" + type + "，支持的类型：add、delete、update";
            }

        } catch (Exception e) {
            return "错误：数据处理失败 - " + e.getMessage();
        }
    }

    private List<MarkdownVo> perfectData(String session) {
        List<MarkdownVo> markdownVos = GenerateData.get(session);
        List<MarkdownEntity> markdownEntities = OriginalData.get(session);

        for (int i = 0; i < markdownVos.size(); i++) {
            MarkdownVo markdownVo = markdownVos.get(i);
            List<String> ids = markdownVo.getIds();

            List<MarkdownEntity> list = markdownEntities.stream().filter(item -> ids.contains(item.getId())).toList();
            if (CollectionUtil.isNotEmpty(list)) {
                String md = list.stream().map(MarkdownEntity::getMd).filter(StrUtil::isEmpty).collect(Collectors.joining("\n"));
                markdownVo.setMd(md);
                markdownVo.setOriginalData(list);
            }
        }
        return markdownVos;
    }


}
