package com.gklx.mopga.admin.module.generate.manager;

import com.gklx.mopga.admin.module.generate.domain.entity.TDocumentVersionEntity;
import com.gklx.mopga.admin.module.generate.dao.TDocumentVersionDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 文档版本历史表  Manager
 *
 * @Author gklx
 * @Date 2026-06-12 09:55:03
 * @Copyright 1.0
 */
@Service
public class TDocumentVersionManager extends ServiceImpl<TDocumentVersionDao, TDocumentVersionEntity> {
}