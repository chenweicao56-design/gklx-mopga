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

    @Schema(description = "是否需要分页接口")
    private Boolean isPage;

    @Schema(description = "是否需要详情接口")
    private Boolean isDetail;

    @Schema(description = "是否需要增加接口")
    private Boolean isAdd;

    @Schema(description = "是否修改需要更新接口")
    private Boolean isUpdate;

    @Schema(description = "是否需要删除接口")
    private Boolean isDelete;

    @Schema(description = "是否需要批量删除接口")
    private Boolean isBatchDelete;

}