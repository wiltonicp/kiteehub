package com.kiteehub.knowledge.modular.attach.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T>
 */
@Data
public class Tree<T> {

    private String id;

    private String label;

    private List<CityTreeNode> children;

    private String parentId;

    private Integer isTemp;

    private boolean hasParent = false;

    private boolean hasChildren = false;

    public void initChildren() {
        this.children = new ArrayList<>();
    }
}
