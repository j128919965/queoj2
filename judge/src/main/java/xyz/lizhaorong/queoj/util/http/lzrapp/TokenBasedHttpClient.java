package xyz.lizhaorong.queoj.util.http.lzrapp;

import xyz.lizhaorong.queoj.support.Response;
import xyz.lizhaorong.queoj.util.http.HttpClient;
import xyz.lizhaorong.queoj.util.http.ResendableHttpClient;

import java.io.IOException;

/**
 * 一个demo，演示用法
 *
 */
public class TokenBasedHttpClient extends ResendableHttpClient {

    private final TokenManager tokenManager;

    public TokenBasedHttpClient(HttpClient client) {
        super(client);
        this.tokenManager = new DiskTokenManager();
    }

    public TokenBasedHttpClient(HttpClient client, TokenManager tokenManager) {
        super(client);
        this.tokenManager = tokenManager;
    }

    @Override
    public <T> T get(String url, Object data, Class<T> retClass) throws IOException {
        super.setHeader("A",tokenManager.loadAccessToken());
        return super.get(url, data, retClass);
    }

    @Override
    public <T> T postByJson(String url, Object data, Class<T> retClass) throws IOException {
        super.setHeader("A",tokenManager.loadAccessToken());
        return super.postByJson(url, data, retClass);
    }

    @Override
    public <T> T post(String url, Object data, Class<T> retClass) throws IOException {
        super.setHeader("A",tokenManager.loadAccessToken());
        return super.post(url, data, retClass);
    }

    @Override
    protected <T> boolean needResend(T ret) {
        if (ret instanceof Response){
            if (((Response) ret).getData().equals(3));
            return true;
        }else return false;
    }

    @Override
    protected void prepareForResend() {
        // super.post(refresh,tokenManager.loadRefreshToken());
        // tokenManager.saveAccessToken();
        // tokenManager.saveRefreshToken();
        // setHeader
    }
}
