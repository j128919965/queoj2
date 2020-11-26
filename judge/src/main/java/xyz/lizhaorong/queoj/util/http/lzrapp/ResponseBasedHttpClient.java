package xyz.lizhaorong.queoj.util.http.lzrapp;

import xyz.lizhaorong.queoj.support.Response;
import xyz.lizhaorong.queoj.util.http.BaseHttpClient;

import java.io.IOException;

public class ResponseBasedHttpClient extends BaseHttpClient {

    @Override
    public <T> T get(String url, Object data, Class<T> retClass) throws IOException {
        Response res = super.get(url, data, Response.class);
        return jsonMapper.convertValue(res.getData(),retClass);
    }

    @Override
    public <T> T post(String url, Object data, Class<T> retClass) throws IOException {
        Response res = super.post(url, data, Response.class);
        return jsonMapper.convertValue(res.getData(),retClass);
    }

    @Override
    public <T> T postByJson(String url, Object data, Class<T> retClass) throws IOException {
        Response res = super.postByJson(url, data, Response.class);
        return jsonMapper.convertValue(res.getData(),retClass);
    }
}
