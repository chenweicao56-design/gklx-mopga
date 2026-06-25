package com.gklx.mopga.admin.ai.rag;

import lombok.Data;

@Data
public class RetrievalVo {

    private String question;

    private float alpha;

    private float score;

    private int limit;


}
