package com.gklx.sync;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 同步控制器，提供文件同步相关的REST API接口
 * 用于接收文件数据并写入本地文件系统
 */
@RestController
public class SyncController {


    /**
     * 同步文件数据到本地文件系统
     *
     * @param datas 包含文件路径和内容的JSON对象列表，每个对象应包含：
     *              - filePath: 文件路径（字符串）
     *              - fileContent: 文件内容（字符串）
     * @return 操作结果，成功返回"success"
     */
    @PostMapping("/sync")
    public String sync(@RequestBody List<JSONObject> datas) {
        for (JSONObject data : datas) {
            String filePath = data.getStr("filePath");
            String fileContent = data.getStr("fileContent");

            File file = new File(filePath);
            if(!file.getParentFile().exists()){
                FileUtil.mkdir(file.getParentFile());
            }
            FileUtil.writeString(fileContent, filePath, StandardCharsets.UTF_8);
        }
        return "success";
    }
}
