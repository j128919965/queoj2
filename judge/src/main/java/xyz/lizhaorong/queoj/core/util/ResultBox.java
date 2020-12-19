package xyz.lizhaorong.queoj.core.util;

public class ResultBox {

    public static final Object DEFAULT_RESULT = new Object();

    public volatile Exception exception;

    public volatile Object result = DEFAULT_RESULT;

}
