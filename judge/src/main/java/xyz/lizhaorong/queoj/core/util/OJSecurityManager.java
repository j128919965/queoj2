package xyz.lizhaorong.queoj.core.util;

import java.io.FilePermission;
import java.io.SerializablePermission;
import java.lang.reflect.ReflectPermission;
import java.net.NetPermission;
import java.net.SocketPermission;
import java.security.Permission;
import java.security.SecurityPermission;
import java.util.PropertyPermission;

public class OJSecurityManager extends SecurityManager{
    public static final int EXIT = 9999;

    /**
     * 重写强行退出检测
     * 防止用户自行终止虚拟机的运行,但是调用程序端可以执行退出
     * */
    public void checkExit(int status) {
        if (status != EXIT)
            throw new SecurityException("Exit On Client Is Not Allowed!");
    }

    /**
     * 	策略权限查看
     * 当执行操作时调用,如果操作允许则返回,操作不允许抛出SecurityException
     * */
    private void sandboxCheck(Permission perm) throws SecurityException {
        // 设置只读属性
        if (perm instanceof SecurityPermission) {
            return;
        } else if (perm instanceof PropertyPermission) {
            if (perm.getActions().equals("read")) {
                return;
            }
        } else if (perm instanceof FilePermission) {
            if (perm.getActions().equals("read") || perm.getActions().equals("write")) {
                return;
            }
        } else if (perm instanceof RuntimePermission
                || perm instanceof ReflectPermission
                || perm instanceof SocketPermission
                || perm instanceof SerializablePermission
                || perm instanceof NetPermission
        ){
            return;
        }

        throw new SecurityException(perm.toString());
    }

    @Override
    public void checkPermission(Permission perm) {
        this.sandboxCheck(perm);
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        this.sandboxCheck(perm);
    }
}
