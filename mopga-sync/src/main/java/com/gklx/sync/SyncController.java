package com.gklx.sync;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class SyncController {


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
