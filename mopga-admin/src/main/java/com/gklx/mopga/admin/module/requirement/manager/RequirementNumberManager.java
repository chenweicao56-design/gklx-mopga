package com.gklx.mopga.admin.module.requirement.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gklx.mopga.admin.module.requirement.dao.RequirementDao;
import com.gklx.mopga.admin.module.requirement.domain.entity.RequirementEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 需求编号生成器
 * 规则：[项目编码/前缀] + YYYYMMDD + "-" + 两位序号(按日递增)
 *
 * @author gklx
 */
@Slf4j
@Service
public class RequirementNumberManager {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String GLOBAL_PREFIX = "RQ";

    @Resource
    private RequirementDao requirementDao;

    /**
     * 生成需求编号
     *
     * @param projectCode 项目编码(作为前缀)
     */
    public String generateNumber(String projectCode) {
        String prefix = (projectCode == null || projectCode.isBlank()) ? GLOBAL_PREFIX : projectCode.trim();
        String datePart = LocalDate.now().format(DATE_FORMAT);
        String like = prefix + datePart + "-";

        // 查询当天该前缀下已用最大序号
        LambdaQueryWrapper<RequirementEntity> wrapper = new LambdaQueryWrapper<RequirementEntity>()
                .likeRight(RequirementEntity::getReqNumber, like)
                .orderByDesc(RequirementEntity::getReqNumber)
                .last("LIMIT 1");
        RequirementEntity last = requirementDao.selectOne(wrapper);

        int seq = 1;
        if (last != null && last.getReqNumber() != null) {
            String numStr = last.getReqNumber().substring(like.length());
            try {
                seq = Integer.parseInt(numStr) + 1;
            } catch (NumberFormatException ignored) {
                seq = 1;
            }
        }
        return like + String.format("%02d", seq);
    }
}
