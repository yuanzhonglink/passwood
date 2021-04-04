package com.pass.util.inetwork;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static PoolingHttpClientConnectionManager cm;
    private static CloseableHttpClient defaultClient;
    public static final String ENCODE = "UTF-8";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    private static final int maxTotal = 400;
    private static final int defaultMaxPerRoute = 100;
    private static final int defaultSocketTimeout = 12000;
    private static final int defaultConnectTimeout = 5000;
    private static final int defaultConnectionRequestTimeout = 1000;

    static {
        RegistryBuilder<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.create();
        // 配置同时支持http、https（采用绕过验证的方式处理https请求）
        socketFactoryRegistry.register("https", createSSL()).register("http", PlainConnectionSocketFactory.getSocketFactory());
        // 创建连接池管理器
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry.build());
        // 最大连接数
        cm.setMaxTotal(400);
        // 每路由最大连接数
        cm.setDefaultMaxPerRoute(100);
        // 连接配置：SocketTimeout（获取数据的超时时间)、ConnectTimeout（建立连接的超时）、RequestTimeout（连接池获取到连接的超时时间）
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(12000).setConnectTimeout(5000).setConnectionRequestTimeout(1000).build();
        // 初始化完成
        defaultClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();
    }

    public HttpClientUtil() {
    }

    public static String doGet(String url, Map<String, String> param, int timeout) throws ParseException, IOException {
        String uri = url + convertQueryString(param);
        HttpRequestBase rq = new HttpGet(uri);
        initTimeout(rq, timeout);
        return getResult(rq);
    }

    private static String convertQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        if (params == null) {
            return "";
        } else {
            String query = "?";

            String name;
            for(Iterator var3 = params.keySet().iterator(); var3.hasNext(); query = query + name + "=" + URLEncoder.encode((String)params.get(name), "UTF-8") + "&") {
                name = (String)var3.next();
            }

            query = query.substring(0, query.length() - 1);
            return query;
        }
    }

    public static String doPostJson(String url, String jsonStr, int timeout) throws ParseException, IOException {
        HttpPost rq = new HttpPost(url);
        initTimeout(rq, timeout);
        rq.addHeader("Content-Type", "application/json");
        StringEntity se = new StringEntity(jsonStr, "UTF-8");
        se.setContentType("application/json");
        se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
        rq.setEntity(se);
        return getResult(rq);
    }

    public static String doPostForm(String url, Map<String, String> param, int timeout) throws ParseException, IOException {
        List<NameValuePair> nvps = new ArrayList();
        if (param != null) {
            Iterator var5 = param.keySet().iterator();

            while(var5.hasNext()) {
                String name = (String)var5.next();
                String value = (String)param.get(name);
                if (value != null) {
                    nvps.add(new BasicNameValuePair(name, value));
                }
            }
        }

        return doPostForm(url, (List)nvps, timeout);
    }

    public static String doPostForm(String url, JSONObject param, int timeout) throws ParseException, IOException {
        List<NameValuePair> nvps = new ArrayList();
        if (param != null) {
            Iterator var5 = param.keySet().iterator();

            while(var5.hasNext()) {
                String name = (String)var5.next();
                String value = param.get(name).toString();
                if (value != null) {
                    nvps.add(new BasicNameValuePair(name, value));
                }
            }
        }

        return doPostForm(url, (List)nvps, timeout);
    }

    private static String doPostForm(String url, List<NameValuePair> nvps, int timeout) throws ParseException, IOException {
        HttpPost rq = new HttpPost(url);
        initTimeout(rq, timeout);
        rq.addHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity se = null;
        se = new UrlEncodedFormEntity(nvps, "UTF-8");
        se.setContentType("application/x-www-form-urlencoded");
        se.setContentEncoding(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
        rq.setEntity(se);
        return getResult(rq);
    }

    public static String doPostHsf(String url, Object[] paramTypes, Object[] paramValues, int timeout) throws ParseException, IOException {
        Map<String, String> map = new HashMap();
        map.put("ArgsTypes", JSONObject.toJSONString(paramTypes));
        map.put("ArgsObjects", JSONObject.toJSONString(paramValues));
        return doPostForm(url, (Map)map, timeout);
    }

    public static String buildHsfUrl(String slbUrl, String interfaceName, String hsfVersion, String methodName) {
        String url = "http://" + slbUrl + "/" + interfaceName + ":" + hsfVersion + "/" + methodName;
        return url;
    }

    private static void initTimeout(HttpRequestBase rq, int timeout) {
        if (timeout > 0) {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            rq.setConfig(requestConfig);
        }

    }

    public static CloseableHttpClient getHttpClient() {
        return defaultClient;
    }

    public static SSLConnectionSocketFactory createSSL() {
        SSLContext sslContext = null;

        try {
            sslContext = (new SSLContextBuilder()).loadTrustMaterial(KeyUtil.getJks()).build();
        } catch (Exception var2) {
        }

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
        return sslConnectionSocketFactory;
    }

    public CloseableHttpClient getDefaultClient() {
        return defaultClient;
    }

    private static String getResult(HttpRequestBase request) throws ParseException, IOException {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;

        String var6;
        try {
            response = httpClient.execute(request);
            entity = response.getEntity();
            if (entity == null) {
                return "";
            }

            String result = EntityUtils.toString(entity, "UTF-8");
            var6 = result;
        } finally {
            try {
                if (entity != null) {
                    EntityUtils.consume(entity);
                }

                if (response != null) {
                    response.close();
                }
            } catch (IOException var12) {
            }

        }

        return var6;
    }
}
