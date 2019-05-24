package com.pass.api.consul;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * consul
 *
 * @author yuanzhonglin
 * @date 2019/5/22
 */
public class RestFulConsulApi {
    public static void main(String[] args) throws Exception{


        // 获取本地注册的服务
        getMethod("http://192.168.32.15:9500/v1/agent/services");

        // 返回agent在集群中看到的成员
        getMethod("http://192.168.32.15:9500/v1/agent/members");

        // 返回本地agent注册的所有检查(包括配置文件和HTTP接口)
        getMethod("http://192.168.32.15:9500/v1/agent/checks");

        // 返回本地agent的配置和成员信息
        getMethod("http://192.168.32.15:9500/v1/agent/self");

        // 触发本地agent加入node
        //getMethod("http://192.168.32.15:9500/v1/agent/join/192.168.32.15");

        putMethod("http://192.168.32.15:9500/v1/agent/service/deregister/consul-client-1103");

    }

    //get
    public static void getMethod(String url) throws Exception {
        URL restURL = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();

        conn.setRequestMethod("GET"); // POST GET PUT DELETE
        conn.setRequestProperty("Content-Type", "application/json");

        Object response = getResponse(conn);
        System.out.println(JSONObject.toJSON(response));
    }

    //post
    public static void postMethod(String url) throws Exception {
        URL restURL = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        Object response = getResponse(conn);
        System.out.println(JSONObject.toJSON(response));
    }

    //put
    public static void putMethod(String url) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        HttpResponse response = httpClient.execute(httpPut);
    }

    public static Object getResponse(HttpURLConnection conn) throws Exception{
        Map<String, Object> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null ){
            sb.append(line);
        }
        br.close();

        Object parse = JSONObject.parse(sb.toString());
        return parse;
    }
}
