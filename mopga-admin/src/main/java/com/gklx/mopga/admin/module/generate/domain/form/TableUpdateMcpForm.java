package com.gklx.mopga.admin.module.generate.domain.form;

import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * $表 列表VO
 *
 * @Author gklx
 * @Date 2026-05-12 09:16:33
 * @Copyright 1.0
 */

@Data
public class TableUpdateMcpForm {

    @Schema(description = "ID")
    private Long tableId;

    @Schema(description = "是否分页（1是）")
    private Boolean isPage;

    @Schema(description = "是否详情（1是）")
    private Boolean isDetail;

    @Schema(description = "是否增加（1是）")
    private Boolean isAdd;

    @Schema(description = "是否修改（1是）")
    private Boolean isUpdate;

    @Schema(description = "是否删除（1是）")
    private Boolean isDelete;

    @Schema(description = "是否批量删除（1是）")
    private Boolean isBatchDelete;

}