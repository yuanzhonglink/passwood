package com.pass.api.lambda;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2021/3/23
 */
public class MapSort {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("2", "a");
        map.put("1", "a");
        map.put("5", "a");
        map.put("6", "a");
        desc(map);

        asc(map);
    }

    public static void desc(Map<String, String> map) {
        System.out.println("--------desc----------");
        Map<String, String> finalMap = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<String, String>comparingByKey()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        finalMap.entrySet().stream().forEach(s -> System.out.println(s.getKey()));
        System.out.println("--------desc----------");
    }

    public static void asc(Map<String, String> map) {
        System.out.println("--------asc----------");
        Map<String, String> finalMap = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        finalMap.entrySet().stream().forEach(s -> System.out.println(s.getKey()));
        System.out.println("--------asc----------");
    }
}
