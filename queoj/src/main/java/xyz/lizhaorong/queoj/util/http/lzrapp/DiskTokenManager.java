package xyz.lizhaorong.queoj.util.http.lzrapp;

public class DiskTokenManager implements TokenManager {

    private String accessToken;

    private String refreshToken;

    public String loadAccessToken() {
        return accessToken;
    }

    public void saveAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String loadRefreshToken() {
        return refreshToken;
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
