package xyz.lizhaorong.queoj.util.http;

import java.io.IOException;

/**
 * 整体采用装饰器模式
 * 主要是为了防止有多重需求
 * 比如既要可以重发、又要异步发送
 * 使用装饰器模式会比较灵活
 */
public interface HttpClient {

    HttpClient setHeader(String k, String v);

    <T>T get(String url, Object data, Class<T> retClass) throws IOException;

    <T>T post(String url, Object data, Class<T> retClass) throws IOException;

    <T>T postByJson(String url, Object data, Class<T> retClass) throws IOException;

}