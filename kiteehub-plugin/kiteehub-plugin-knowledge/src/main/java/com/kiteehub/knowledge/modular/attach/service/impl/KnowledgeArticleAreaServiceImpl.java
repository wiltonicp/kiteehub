package com.kiteehub.knowledge.modular.attach.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeArticleArea;
import com.kiteehub.knowledge.modular.attach.mapper.KnowledgeArticleAreaMapper;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeArticleAreaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章区域表Service接口实现类
 *
 * @author Ranger
 * @date 2023/01/01 14:52
 **/
@Service
public class KnowledgeArticleAreaServiceImpl extends ServiceImpl<KnowledgeArticleAreaMapper, KnowledgeArticleArea> implements KnowledgeArticleAreaService {

    @Override
    public void addOrEdit(String articleId, List<String> areaIds) {
        if(CollectionUtil.isNotEmpty(areaIds)){
            //区域处理
            QueryWrapper<KnowledgeArticleArea> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(KnowledgeArticleArea::getArticleId,articleId);
            this.remove(queryWrapper);
            List<KnowledgeArticleArea> collect = areaIds.stream()
                    .map(c ->
                            KnowledgeArticleArea.builder()
                                    .articleId(articleId)
                                    .areaId(c)
                                    .build())
                    .collect(Collectors.toList());
            this.saveBatch(collect,200);
        }
    }
}
