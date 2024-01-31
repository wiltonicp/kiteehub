/*
 * Copyright [2022] [https://www.kiteehub.com]
 *
 * Kiteehub是内部代码，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Kiteehub源码头部的版权声明。
 * 3.本项目代码使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.kiteehub.com
 * 5.本项目只可用于内部开发，如有问题可联系团队wilton.icp@gmail.com商议合作。
 */
package com.kiteehub.knowledge.modular.subsidy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatchData;
import com.kiteehub.knowledge.modular.subsidy.param.*;
import com.kiteehub.knowledge.modular.subsidy.service.KbSubsidyBatchDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatch;
import com.kiteehub.knowledge.modular.subsidy.mapper.KbSubsidyBatchMapper;
import com.kiteehub.knowledge.modular.subsidy.service.KbSubsidyBatchService;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 补贴公示Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/31 14:12
 **/
@Service
@AllArgsConstructor
public class KbSubsidyBatchServiceImpl extends ServiceImpl<KbSubsidyBatchMapper, KbSubsidyBatch> implements KbSubsidyBatchService {

    private final KbSubsidyBatchDataService kbSubsidyBatchDataService;

    @Override
    public Page<KbSubsidyBatch> page(KbSubsidyBatchPageParam kbSubsidyBatchPageParam) {
        QueryWrapper<KbSubsidyBatch> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(kbSubsidyBatchPageParam.getSubsidyType())) {
            queryWrapper.lambda().eq(KbSubsidyBatch::getSubsidyType, kbSubsidyBatchPageParam.getSubsidyType());
        }
        if(ObjectUtil.isAllNotEmpty(kbSubsidyBatchPageParam.getSortField(), kbSubsidyBatchPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(kbSubsidyBatchPageParam.getSortOrder());
            queryWrapper.orderBy(true, kbSubsidyBatchPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(kbSubsidyBatchPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KbSubsidyBatch::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject importSubsidyBatch(KbSubsidyBatchAddParam kbSubsidyBatchAddParam) {
        KbSubsidyBatch kbSubsidyBatch = this.lambdaQuery()
                .eq(KbSubsidyBatch::getSubsidyType, kbSubsidyBatchAddParam.getSubsidyType())
                .one();
        if(ObjectUtil.isEmpty(kbSubsidyBatch)){
            kbSubsidyBatch = new KbSubsidyBatch();
            kbSubsidyBatch.setSubsidyType(kbSubsidyBatchAddParam.getSubsidyType());
            kbSubsidyBatch.setBatch(1);
            kbSubsidyBatch.setUpdateTime(new Date());
            this.save(kbSubsidyBatch);
        }else {
            kbSubsidyBatch.setBatch(kbSubsidyBatch.getBatch() + 1);
            this.updateById(kbSubsidyBatch);
        }
        try {
            int successCount = 0;
            int errorCount = 0;
            JSONArray errorDetail = JSONUtil.createArray();
            // 创建临时文件
            File tempFile = FileUtil.writeBytes(kbSubsidyBatchAddParam.getFile().getBytes(), FileUtil.file(FileUtil.getTmpDir() +
                    FileUtil.FILE_SEPARATOR + "subsidyBatchImportTemplate.xlsx"));
            // 读取excel
            List<KbSubsidyBatchDataImportParam> kbSubsidyBatchImportParamList =  EasyExcel.read(tempFile).head(KbSubsidyBatchDataImportParam.class).sheet()
                    .headRowNumber(2).doReadSync();
            for (int i = 0; i < kbSubsidyBatchImportParamList.size(); i++) {
                JSONObject jsonObject = this.doImport(kbSubsidyBatch.getId(), kbSubsidyBatchImportParamList.get(i), i);
                if(jsonObject.getBool("success")) {
                    successCount += 1;
                } else {
                    errorCount += 1;
                    errorDetail.add(jsonObject);
                }
            }
            return JSONUtil.createObj()
                    .set("totalCount", kbSubsidyBatchImportParamList.size())
                    .set("successCount", successCount)
                    .set("errorCount", errorCount)
                    .set("errorDetail", errorDetail);
        } catch (Exception e) {
            log.error(">>> 补贴导入失败：", e);
            throw new CommonException("补贴导入失败");
        }
    }

    /**
     * 执行导入
     * @param batchId
     * @param kbSubsidyBatchDataImportParam
     * @param i
     * @return
     */
    private JSONObject doImport(String batchId, KbSubsidyBatchDataImportParam kbSubsidyBatchDataImportParam, int i) {
        try {
            KbSubsidyBatchData kbSubsidyBatchData = new KbSubsidyBatchData();

            // 拷贝属性
            BeanUtil.copyProperties(kbSubsidyBatchDataImportParam, kbSubsidyBatchData);

            kbSubsidyBatchData.setBatchId(batchId);

            kbSubsidyBatchDataService.save(kbSubsidyBatchData);
            // 返回成功
            return JSONUtil.createObj().set("success", true);
        }catch (Exception e){
            log.error(">>> 补贴数据导入异常：", e);
            return JSONUtil.createObj().set("success", false).set("index", i + 1).set("msg", "补贴数据导入异常");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KbSubsidyBatchEditParam kbSubsidyBatchEditParam) {
        KbSubsidyBatch kbSubsidyBatch = this.queryEntity(kbSubsidyBatchEditParam.getId());
        BeanUtil.copyProperties(kbSubsidyBatchEditParam, kbSubsidyBatch);
        this.updateById(kbSubsidyBatch);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KbSubsidyBatchIdParam> kbSubsidyBatchIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(kbSubsidyBatchIdParamList, KbSubsidyBatchIdParam::getId));
    }

    @Override
    public KbSubsidyBatch detail(KbSubsidyBatchIdParam kbSubsidyBatchIdParam) {
        return this.queryEntity(kbSubsidyBatchIdParam.getId());
    }

    @Override
    public KbSubsidyBatch queryEntity(String id) {
        KbSubsidyBatch kbSubsidyBatch = this.getById(id);
        if(ObjectUtil.isEmpty(kbSubsidyBatch)) {
            throw new CommonException("补贴公示不存在，id值为：{}", id);
        }
        return kbSubsidyBatch;
    }
}
