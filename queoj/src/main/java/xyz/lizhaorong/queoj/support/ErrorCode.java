package xyz.lizhaorong.queoj.support;

/**
 * 虚类，errorcode
 * 方便宏定义错误类型
 */
public abstract class ErrorCode {

    protected String message;
    protected int code;

    public ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String message() {
        return message;
    }

    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}