package com.gklx.mopga.admin.module.generate.domain.form.text2sql;

import lombok.Data;

import java.util.List;

@Data
public class Text2sqlQueryForm {

    private Long databaseId;

    private List<Long> tableIds;
}
