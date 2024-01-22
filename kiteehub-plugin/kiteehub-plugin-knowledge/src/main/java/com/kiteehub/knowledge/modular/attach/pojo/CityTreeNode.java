package com.kiteehub.knowledge.modular.attach.pojo;

/**
 * 城市树
 *
 * @author Ranger
 * @date  2023/12/27 14:00
 **/
public class CityTreeNode extends Tree<CityTreeNode> {
    private int attachCount;

    public int getAttachCount() {
        return attachCount;
    }

    public void setAttachCount(int attachCount) {
        this.attachCount = attachCount;
    }
}
