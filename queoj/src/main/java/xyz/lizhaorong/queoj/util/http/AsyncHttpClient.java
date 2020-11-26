package xyz.lizhaorong.queoj.util.http;

/**
 * 异步httpClient
 */
public interface AsyncHttpClient extends HttpClient {

    AsyncHttpClient success(CallBack success);

    AsyncHttpClient failed(CallBack failed);

    AsyncHttpClient setHeader(String k, String v);

    <T>void getAsync(String url, Object data, Class<T> retClass);

    <T>void postAsync(String url, Object data, Class<T> retClass);

    <T>void postByJsonAsync(String url, Object data, Class<T> retClass);

}
