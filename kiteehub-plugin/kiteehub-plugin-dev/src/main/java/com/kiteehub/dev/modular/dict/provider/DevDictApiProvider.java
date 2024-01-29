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
package com.kiteehub.dev.modular.dict.provider;

import com.kiteehub.dev.core.pojo.DictNode;
import com.kiteehub.dev.modular.dict.mapper.DevDictMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.kiteehub.dev.api.DevDictApi;

import java.util.List;

/**
 * 字典API接口实现类
 *
 * @author xuyuxiang
 * @date 2022/9/2 16:05
 */
@Service
@AllArgsConstructor
public class DevDictApiProvider implements DevDictApi {

    private final DevDictMapper devDictMapper;

    @Override
    public List<String> getIdsByParentIds(List<String> parentIds) {
        return devDictMapper.getIdsByParentIds(parentIds);
    }

    @Override
    public List<DictNode> dictTree(String parentId) {
        return devDictMapper.dictTree(parentId);
    }
}
