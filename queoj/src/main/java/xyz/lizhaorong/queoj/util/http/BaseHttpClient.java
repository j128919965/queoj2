package xyz.lizhaorong.queoj.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础的发送http请求实现，基于apache httpClient的实现
 * @author lzr
 */
public class BaseHttpClient implements HttpClient {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    protected Map<String, String> headers = new HashMap<>();

    @Override
    public HttpClient setHeader(String k, String v) {
        headers.put(k,v);
        return this;
    }

    @Override
    public <T> T get(String url, Object data, Class<T> retClass) throws IOException {
        url = transferQueryString(url,data);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        headers.forEach(httpGet::setHeader);
        CloseableHttpResponse response = client.execute(httpGet);
        String back = checkAndGetResponse(response);
        client.close();
        return jsonMapper.readValue(back,retClass);
    }

    @Override
    public <T> T post(String url, Object data, Class<T> retClass) throws IOException {
        Map<String, Object> map = transferMap(data);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> content = new ArrayList<>();
        map.forEach((k,v)->{
            content.add(new BasicNameValuePair(k,String.valueOf(v)));
        });
        HttpPost httpPost = new HttpPost(url);
        //设置http头
        headers.forEach(httpPost::setHeader);
        HttpEntity entity = new UrlEncodedFormEntity(content, "UTF-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String back = checkAndGetResponse(response);
        return jsonMapper.readValue(back,retClass);
    }

    @Override
    public <T> T postByJson(String url, Object data, Class<T> retClass) throws IOException {
        String json = transferJsonString(data);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 设置post body
        StringEntity entity = new StringEntity(json, Consts.UTF_8);
        entity.setContentType("application/json");
        HttpPost httpPost = new HttpPost(url);
        //设置http头
        headers.forEach(httpPost::setHeader);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String back = checkAndGetResponse(response);
        httpClient.close();
        return jsonMapper.readValue(back,retClass);
    }

    /**
     * 检查响应状态，并获取数据
     * @param response CloseableHttpResponse
     * @return 字符串数据
     */
    private String checkAndGetResponse(CloseableHttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        String back = EntityUtils.toString(response.getEntity(),"UTF-8");
        response.close();
        return back;
    }

    /**
     * 将url和数据转化为url传参
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    private String transferQueryString(String url, Object data) throws IOException {
        if (data==null){
            return url;
        }
        Map<String, String> map;
        StringBuilder sb = new StringBuilder(url);
        sb.append('?');
        if(!(data instanceof Map)){
            String s = jsonMapper.writeValueAsString(data);
            map = jsonMapper.readValue(s, Map.class);
        } else {
            map = (Map)data;
        }
        map.forEach((k,v)->{
            sb.append(k).append('=').append(v).append('&');
        });
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     * 将数据转化为Json字符串
     * @param data
     * @return
     * @throws JsonProcessingException
     */
    private String transferJsonString(Object data) throws JsonProcessingException {
        if (data==null){
            return "";
        }
        return jsonMapper.writeValueAsString(data);
    }

    private Map<String, Object> transferMap(Object data) throws JsonProcessingException {
        if (data==null){
            return new HashMap<>(0);
        }
        String s = jsonMapper.writeValueAsString(data);
        return jsonMapper.readValue(s, Map.class);
    }

}
