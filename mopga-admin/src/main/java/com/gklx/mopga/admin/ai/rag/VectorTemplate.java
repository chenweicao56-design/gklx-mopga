package com.gklx.mopga.admin.ai.rag;

import cn.hutool.core.util.StrUtil;
import com.gklx.mopga.admin.ai.model.EmbeddingModel;
import com.gklx.mopga.admin.ai.model.RerankModel;
import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.List;

public abstract class VectorTemplate {


    JiebaSegmenter segmenter = new JiebaSegmenter();

    RerankModel rerankModel = new RerankModel();

    EmbeddingModel embeddingModel = new EmbeddingModel();



    public String participle(String text) {
        List<String> list = segmenter.sentenceProcess(text);
        return StrUtil.join(" ", list);
    }

}
