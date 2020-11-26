package xyz.lizhaorong.queoj.util.http;

import java.io.IOException;

/**
 * 基于单独开启线程的异步发送请求
 */
public class ThreadAsyncHttpClient implements AsyncHttpClient{

    private final BaseHttpClient client;

    private CallBack success;
    private CallBack failed;

    public ThreadAsyncHttpClient(BaseHttpClient client) {
        this.client = client;
    }

    @Override
    public AsyncHttpClient setHeader(String k, String v) {
        client.setHeader(k,v);
        return this;
    }

    @Override
    public <T> T get(String url, Object data, Class<T> retClass) throws IOException {
        return client.get(url, data, retClass);
    }

    @Override
    public <T> T post(String url, Object data, Class<T> retClass) throws IOException {
        return client.post(url, data, retClass);
    }

    @Override
    public <T> T postByJson(String url, Object data, Class<T> retClass) throws IOException {
        return client.postByJson(url, data, retClass);
    }

    @Override
    public AsyncHttpClient success(CallBack success) {
        this.success=success;
        return this;
    }

    @Override
    public AsyncHttpClient failed(CallBack failed) {
        this.failed=failed;
        return this;
    }

    @Override
    public <T>void getAsync(String url, Object data, Class<T> retClass){
        new Thread(()->{
            T obj;
            try {
                obj = get(url,data,retClass);
            } catch (IOException e) {
                e.printStackTrace();
                if (this.failed!=null){
                    this.failed.call(e);
                }
                return;
            }
            if (this.success!=null){
                success.call(obj);
            }
        }).start();
    }

    @Override
    public <T>void postAsync(String url, Object data, Class<T> retClass){
        new Thread(()->{
            T obj;
            try {
                obj = post(url,data,retClass);
            } catch (IOException e) {
                e.printStackTrace();
                if (this.failed!=null){
                    this.failed.call(e);
                }
                return;
            }
            if (this.success!=null){
                success.call(obj);
            }
        }).start();
    }

    @Override
    public <T>void postByJsonAsync(String url, Object data, Class<T> retClass){
        new Thread(()->{
            T obj;
            try {
                obj = postByJson(url,data,retClass);
            } catch (IOException e) {
                e.printStackTrace();
                if (this.failed!=null){
                    this.failed.call(e);
                }
                return;
            }
            if (this.success!=null){
                success.call(obj);
            }
        }).start();
    }

}
