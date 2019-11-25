package com.pass.api;

import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2019/11/25
 */
public interface DubboApi {
    Map<String, Object> getMsg(int index);
}
