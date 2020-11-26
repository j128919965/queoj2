package xyz.lizhaorong.queoj.util.http;

import java.io.IOException;

/**
 * 支持重新发送的http客户端
 */
public abstract class ResendableHttpClient implements HttpClient {

    private final HttpClient client;

    public ResendableHttpClient(HttpClient client) {
        this.client = client;
    }

    /**
     * 根据请求的返回值来判断是否需要重新发送
     * @param ret 上次请求的返回值
     * @return 是否需要重发
     */
    protected abstract <T>boolean needResend(T ret);

    /**
     * 重发请求前做准备工作（如更新token等）
     */
    protected abstract void prepareForResend();

    @Override
    public <T> T get(String url, Object data, Class<T> retClass) throws IOException {
        T ret = client.get(url,data,retClass);
        if (needResend(ret)){
            prepareForResend();
            ret = client.get(url,data,retClass);
        }
        return ret;
    }

    @Override
    public <T> T post(String url, Object data, Class<T> retClass) throws IOException {
        T ret = client.post(url,data,retClass);
        if (needResend(ret)){
            prepareForResend();
            ret = client.post(url,data,retClass);
        }
        return ret;
    }

    @Override
    public <T> T postByJson(String url, Object data, Class<T> retClass) throws IOException {
        T ret = client.postByJson(url,data,retClass);
        if (needResend(ret)){
            prepareForResend();
            ret = client.postByJson(url,data,retClass);
        }
        return ret;
    }

    @Override
    public ResendableHttpClient setHeader(String k, String v) {
        client.setHeader(k, v);
        return this;
    }
}
