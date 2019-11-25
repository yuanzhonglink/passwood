package com.pass.dubbo.server;

import com.alibaba.dubbo.config.annotation.Service;
import com.pass.api.DubboApi;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2019/11/25
 */
@Service
@Slf4j
public class DubboServer implements DubboApi {

    @Override
    public Map<String, Object> getMsg(int index) {
        log.info("第["+ index +"]请求...");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "TOM");
        return map;
    }
}
