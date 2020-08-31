package com.coderlong.poc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.nio.charset.Charset;

@Slf4j
public class HttpUtil {
    static HttpClient httpClient = null;
    private static RequestConfig default_request_config = null;
    // 连接超时
    private static final int Connection_Timeout = 1000 * 60 * 2;
    // 指定时间内服务器端没有反应
    private static final int Socket_Timeout = 1000 * 40;
    // 请求超时
    private static final int Request_Timeout = 1000 * 40;
    // 每个主机
    private static final int Max_Host_Connections = 200;
    // 总的连接数
    private static final int Max_Total_Connections = 2000;
    private static final String Utf8 = "utf-8";
    public static String Success_Key = "success";


    static {
        default_request_config = RequestConfig.custom().setSocketTimeout(Socket_Timeout).setConnectTimeout(
                Connection_Timeout).setConnectionRequestTimeout(Request_Timeout).build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(Max_Total_Connections);
        connMgr.setDefaultMaxPerRoute(Max_Host_Connections);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(connMgr);
        httpClientBuilder.setDefaultRequestConfig(default_request_config);

        ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charset.forName(Utf8)).build();

        httpClientBuilder.setDefaultConnectionConfig(connConfig);
        httpClient = httpClientBuilder.build();
    }


    public static JSONObject post(String cookie, String bfc_token, String param) throws Exception {
        String url = "https://poblockchain.club/web_api/star/createOrder";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        StringEntity requestEntity = new StringEntity(param,"utf-8");
        requestEntity.setContentType("application/json");
        requestEntity.setContentEncoding("utf-8");

        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Content-type","application/json");
        httpPost.setHeader("cookie", cookie);
        httpPost.setHeader("bfc_token", bfc_token);
        httpPost.setHeader("bfc-language", "CN");


        httpPost.setEntity(requestEntity);
        Object returnValue = new Object();
        try{
            returnValue=httpClient.execute(httpPost,responseHandler);

        } catch (Exception e){
            log.error(String.format("HttpUtil post error, url=%s, param=%s, msg=%s", url, param, e.getMessage()), e);
        } finally {
            httpClient.close();
        }

        //System.out.printf(returnValue);

        return JSONObject.parseObject(returnValue==null ? "" : returnValue.toString());
    }

    public static JSONObject post2(String cookie, String bfc_token, String param) throws Exception {
        String url = "https://poblockchain.club/web_api/star/createOrder";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        StringEntity requestEntity = new StringEntity(param,"utf-8");
        requestEntity.setContentType("application/json");
        requestEntity.setContentEncoding("utf-8");

        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Content-type","application/json");
        httpPost.setHeader("cookie", cookie);
        httpPost.setHeader("bfc_token", bfc_token);


        httpPost.setEntity(requestEntity);


        //执行请求操作，并拿到结果（同步阻塞）
        String body = "";
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);

            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        return JSONObject.parseObject(body);
    }


    /**
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static JSONObject get(String url, JSONObject params) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (params == null) {
            params = new JSONObject();
        }
        for (String key : params.keySet()) {
            String value = params.getString(key);
            if (value == null) {
                continue;
            }

            value = URLEncoder.encode(value, "utf8");
            sb.append(key + "=" + value).append("&");
        }

        if(!StringUtils.isEmpty(sb.toString())){
            url = url + (url.contains("?") ? "&" : "?") + sb;
        }

        HttpRequestBase method = null;

        method = new HttpGet(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
        method.setConfig(requestConfig);

        JSONObject jobj = new JSONObject();
        // 建立一个NameValuePair数组，用于存储欲传送的参数
        method.addHeader("Content-type", "application/json; charset=utf-8");
        method.setHeader("Accept", "application/json");
        HttpResponse response = httpClient.execute(method);
        String body = EntityUtils.toString(response.getEntity(), Utf8);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            jobj.put(Success_Key, true);

            jobj.put("data", body);
        } else {
            jobj.put("msg", body);
            jobj.put(Success_Key, false);
        }

        response.getEntity().getContent().close();
        return jobj;
    }
}
