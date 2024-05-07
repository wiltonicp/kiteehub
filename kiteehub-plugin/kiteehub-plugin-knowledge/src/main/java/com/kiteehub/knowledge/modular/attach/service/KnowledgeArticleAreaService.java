package com.kiteehub.knowledge.modular.attach.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeArticleArea;

import java.util.List;

/**
 * 文章区域表Service接口
 *
 * @author Ranger
 * @date  2023/01/01 14:52
 **/
public interface KnowledgeArticleAreaService extends IService<KnowledgeArticleArea> {

    void addOrEdit(String articleId, List<String> areaIds);
}
