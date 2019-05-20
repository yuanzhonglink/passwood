package com.pass.util.license.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * license补充内容
 *
 * @Author yuanzhonglin
 * @since 2019/4/30
 */
public class LicenseExtraEntity {

    //节点数
    private Integer nodeNum;

    public Integer getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(Integer nodeNum) {
        this.nodeNum = nodeNum;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
