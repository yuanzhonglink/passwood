package com.pass.util.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2020/7/4
 * @Description: yaml读取、操作
 */
public class YamlUtil {
    private final static DumperOptions OPTIONS = new DumperOptions();

    static {
        //将默认读取的方式设置为块状读取
        OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    }

    /**
     * 初始化map，将map中属性包含.的形成多级map结构，方便保存
     */
    public static Map<String, Object> initMap(Map<String, Object> map) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.putAll(map);
        for (String s : map.keySet()) {
            if (map.get(s) == null) {
                map.put(s, "");
            }
            if (s.split("\\.").length > 1) {
                String key[] = s.split("\\.");
                Map<String, Object> tarMap = new LinkedHashMap<>();
                for (int i = 0; i < key.length; i++) {
                    if (i == key.length - 1) {
                        //最后一位
                        tarMap.put(key[i], map.get(s));
                    } else {
                        if (result.get(key[i]) != null) {
                            tarMap = (Map<String, Object>) result.get(key[i]);
                        } else {
                            tarMap = (Map<String, Object>) tarMap.get(key[i]);
                        }
                    }
                }
                result.remove(s);
            }
        }
        return result;
    }

    public static void addIntoYaml(String dest, Map<String, Object> map) throws IOException {
        map = initMap(map);
        Yaml yaml = new Yaml(OPTIONS);
        //载入当前yml文件
        LinkedHashMap<String, Object> dataMap = (LinkedHashMap<String, Object>) yaml.load(new FileReader(dest));
        //如果yml内容为空,则会引发空指针异常,此处进行判断
        if (null == dataMap) {
            dataMap = new LinkedHashMap<String, Object>();
        }
        dataMap.putAll(map);
        //将数据重新写回文件
        yaml.dump(dataMap, new FileWriter(dest));
    }

    public static LinkedHashMap<String, Object> getYmlMap(String path) {
        File source = new File(path);
        Yaml yaml = new Yaml(OPTIONS);
        //载入文件
        LinkedHashMap<String, Object> dataMap = null;
        try {
            dataMap = (LinkedHashMap<String, Object>) yaml.load(new FileReader(source));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //获取当前key下的值(如果存在多个节点,则value可能为map,自行判断)
        return dataMap;
    }

    public static void main(String args[]) {
        URL url = YamlUtil.class.getClass().getResource("/application.yml");
        Map<String, Object> map = getYmlMap(url.getPath());
        map.put("server.port", 1107);
        map = initMap(map);
        try {
            addIntoYaml(url.getPath(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("");
    }
}