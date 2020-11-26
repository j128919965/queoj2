package xyz.lizhaorong.queoj.util.http.lzrapp;

public interface TokenManager {

    String loadAccessToken();

    void saveAccessToken(String accessToken);

    String loadRefreshToken();

    void saveRefreshToken(String refreshToken);

}
