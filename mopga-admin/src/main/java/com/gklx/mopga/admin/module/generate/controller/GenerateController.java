package com.gklx.mopga.admin.module.generate.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVo;
import com.gklx.mopga.admin.module.generate.service.GenerateService;
import com.gklx.mopga.base.common.domain.ResponseDTO;
import com.gklx.mopga.base.common.domain.ValidateList;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 数据源表 Controller
 *
 * @Author gklx
 * @Date 2025-09-05 09:07:47
 * @Copyright 1
 */

@RestController
@Tag(name = "代码生成")
public class GenerateController {

    @Resource
    private GenerateService generateService;


    @GetMapping("/gen/sync/table/{databaseId}")
    public ResponseDTO<Boolean> syncTable(@PathVariable("databaseId") Long databaseId,
                                          @RequestParam(value = "containColumn") Boolean containColumn) {
        return ResponseDTO.ok(generateService.syncTable(databaseId, containColumn, null));
    }

    @GetMapping("/gen/sync/column/{tableId}")
    public ResponseDTO<Boolean> syncTableColumn(@PathVariable("tableId") Long tableId) {
        return ResponseDTO.ok(generateService.syncTableColumn(tableId));
    }

    @PostMapping("/gen/batch/sync/column")
    public ResponseDTO<Boolean> syncTableColumn(@RequestBody ValidateList<Long> tableIds) {
        return ResponseDTO.ok(generateService.syncTableColumn(tableIds));
    }

    @GetMapping("/gen/preview/{tableId}")
    public ResponseDTO<List<JSONObject>> preview(@PathVariable("tableId") Long tableId) {
        return ResponseDTO.ok(generateService.preview(tableId));
    }

    @PostMapping("/gen/create/table")
    public ResponseDTO<String> createTable(@RequestParam(value = "isSync") Boolean isSync,
                                           @RequestBody TableVo table) {
        return ResponseDTO.ok(generateService.createTable(table, isSync));
    }

    @PostMapping("/gen/create/table/preview")
    public ResponseDTO<String> createTablePreview(@RequestBody TableVo table) {
        return ResponseDTO.ok(generateService.buildCreateTableSqlTemplate(table));
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.write(response.getOutputStream(), true, data);
    }


}
