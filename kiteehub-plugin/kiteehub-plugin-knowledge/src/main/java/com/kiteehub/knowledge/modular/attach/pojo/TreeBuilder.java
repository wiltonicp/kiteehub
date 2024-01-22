package com.kiteehub.knowledge.modular.attach.pojo;

import com.kiteehub.dev.core.pojo.CityNode;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 城市树
 *
 * @author Ranger
 * @date  2023/12/27 14:00
 **/
public class TreeBuilder {

    private final static String TOP_NODE_ID = "0";

    public static List<? extends Tree<?>> buildTree(List<CityNode> devDictList, List<KnowledgeAttachArea> attachAreaList) {
        // 创建字典项的Map，方便根据ID查找
        Map<String, CityNode> dictMap = new HashMap<>();
        for (CityNode dict : devDictList) {
            dictMap.put(dict.getId(), dict);
        }

        // 创建树节点列表
        List<CityTreeNode> nodeList = new ArrayList<>();

        // 遍历省市字典数据列表，创建树节点并统计文件数量
        for (CityNode dict : devDictList) {
            CityTreeNode node = new CityTreeNode();
            node.setId(dict.getId());
            node.setLabel(dict.getDictLabel().equals("系统区域分类") ? "全部" : dict.getDictLabel());
            node.setParentId(dict.getParentId());

            // 统计文件数量
            int attachCount = 0;
            for (KnowledgeAttachArea attachArea : attachAreaList) {
                if (attachArea.getAreaId().equals(dict.getId())) {
                    attachCount++;
                }
            }
            node.setAttachCount(attachCount);

            nodeList.add(node);
        }

        List<CityTreeNode> build = build(nodeList);

        // 统计父节点的文件数量
        for (CityTreeNode node : build) {
            countParentFileCount(node);
        }

        // 过滤掉fileCount为0的节点
        List<CityTreeNode> filteredList = new ArrayList<>();
        for (CityTreeNode node : build) {
            if (node.getAttachCount() > 0) {
                filteredList.add(node);
                filterChildNodes(node);
            }
        }
        return filteredList;
    }

    private static void countParentFileCount(CityTreeNode node) {
        int fileCount = node.getAttachCount();

        if(node.isHasChildren()){
            List<CityTreeNode> children = node.getChildren();
            for (CityTreeNode child : children) {
                countParentFileCount(child);
                fileCount += child.getAttachCount();
            }
        }
        node.setAttachCount(fileCount);
    }

    private static void filterChildNodes(CityTreeNode node) {
        node.setLabel(node.getLabel() + "(" + node.getAttachCount() + ")");
        if(node.isHasChildren()){
            List<CityTreeNode> children = node.getChildren();
            List<CityTreeNode> filteredChildren = new ArrayList<>();
            for (CityTreeNode child : children) {
                if (child.getAttachCount() > 0) {
                    filteredChildren.add(child);
                    filterChildNodes(child);
                }
            }
            node.setChildren(filteredChildren);
        }
    }

    public static List<CityTreeNode> build(List<CityTreeNode> nodes) {
        if (nodes == null) {
            return null;
        }
        List<CityTreeNode> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId();
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (CityTreeNode n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty()) {
                topNodes.add(node);
            }
        });
        return topNodes;
    }
}
