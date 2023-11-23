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
package com.kiteehub.common.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel自定义合并策略 该类继承了AbstractMergeStrategy抽象合并策略，需要重写merge()方法
 *
 * @author xuyuxiang
 * @date 2023/3/6 13:21
 **/
public class CommonExcelCustomMergeStrategy extends AbstractMergeStrategy {

    /**
     * 分组，每几行合并一次
     */
    private final List<Integer> exportFieldGroupCountList;

    /**
     * 目标合并列index
     */
    private final Integer targetColumnIndex;

    /**
     * 需要开始合并单元格的首行index
     */
    private Integer rowIndex;

    /**
     * exportDataList为待合并目标列的值
     *
     * @author xuyuxiang
     * @date 2023/3/6 13:23
     **/
    public CommonExcelCustomMergeStrategy(List<String> exportDataList, Integer targetColumnIndex) {
        this.exportFieldGroupCountList = getGroupCountList(exportDataList);
        this.targetColumnIndex = targetColumnIndex;
    }


    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {

        if (null == rowIndex) {
            rowIndex = cell.getRowIndex();
        }
        // 仅从首行以及目标列的单元格开始合并，忽略其他
        if (cell.getRowIndex() == rowIndex && cell.getColumnIndex() == targetColumnIndex) {
            mergeGroupColumn(sheet);
        }
    }

    private void mergeGroupColumn(Sheet sheet) {
        int rowCount = rowIndex;
        for (Integer count : exportFieldGroupCountList) {
            if(count == 1) {
                rowCount += count;
                continue ;
            }
            // 合并单元格
            CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount + count - 1, targetColumnIndex, targetColumnIndex);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
            rowCount += count;
        }
    }

    /*
     * 该方法将目标列根据值是否相同连续可合并，存储可合并的行数
     *
     * @author xuyuxiang
     * @date 2023/3/6 13:23
     **/
    private List<Integer> getGroupCountList(List<String> exportDataList){
        if (CollectionUtils.isEmpty(exportDataList)) {
            return new ArrayList<>();
        }

        List<Integer> groupCountList = new ArrayList<>();
        int count = 1;

        for (int i = 1; i < exportDataList.size(); i++) {
            if (exportDataList.get(i).equals(exportDataList.get(i - 1))) {
                count++;
            } else {
                groupCountList.add(count);
                count = 1;
            }
        }
        // 处理完最后一条后
        groupCountList.add(count);
        return groupCountList;
    }
}
