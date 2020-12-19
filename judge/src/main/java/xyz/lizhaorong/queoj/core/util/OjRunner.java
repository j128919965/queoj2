package xyz.lizhaorong.queoj.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.lizhaorong.queoj.core.exception.TimeLimitExceedException;
import xyz.lizhaorong.queoj.core.exception.WrongAnswerException;
import xyz.lizhaorong.queoj.core.manager.ProblemManager;
import xyz.lizhaorong.queoj.entity.Problem;
import xyz.lizhaorong.queoj.entity.Record;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Slf4j
public class OjRunner {

    @Autowired
    ProblemManager problemManager;

    public void run(Record record,Class<?> clazz) throws WrongAnswerException, TimeLimitExceedException ,RuntimeException{
        try {
            record.setUsedSpace(-1L);
            Problem p = problemManager.getProblem(record.getPid());

            //调用的方法
            Method method = getMethod(p.getCodesig(),clazz);

            if (method==null)throw new RuntimeException();

            Class<?>[] parameterTypes = method.getParameterTypes();
            String[] paramsAndResult = p.getPendingCode().replaceAll("\r","").split("\n");
            // 一轮循环的长度 返回值+参数列表长度
            int oneTurn = parameterTypes.length+1;
            // 大循环，每次判一个样例
            for (int i=0;i<paramsAndResult.length;i+=oneTurn){
                log.debug("测试样例"+i/3);
                Object[] params = new Object[oneTurn-1];
                for (int j=0;j<oneTurn-1;j++){
                    // 将输入参数转化为合法的格式
                    Object o = convert(paramsAndResult[i+j], parameterTypes[j]);
                    params[j] = o;
                }
                // 将返回值转化为合法的格式
                Object ret = convert(paramsAndResult[i+oneTurn-1],method.getReturnType());
                if (oneTurn==1) params = null;

                // 用一个容器来承载另外一个线程的返回值
                // 为保证线程安全，容器内的变量是volatile
                // 有且只有一个线程读，有且只有一个线程写
                ResultBox box = new ResultBox();
                Invoker invoker = new Invoker(box,method,params);
                invoker.start();
                long start = System.currentTimeMillis();
                for (int j=0;j<50;j++){
                    Thread.sleep(40);
                    // 有异常，那么该线程肯定已经停掉了。
                    if (box.exception!=null){
                        throw new RuntimeException(box.exception);
                    }
                    // box中的对象不再是默认对象，并且结果不同
                    if (box.result!=ResultBox.DEFAULT_RESULT ){
                        record.setUsedTime(System.currentTimeMillis()-start);
                        log.debug("ret : "+ret + " box.result : "+box.result);
                        if (ret.equals(box.result)){
                            record.setUsedSpace(99L);
                            break;
                        }else {
                            throw new WrongAnswerException("结果错误");
                        }
                    }
                    // 已经两秒钟了，超时
                    // 需要强行将用户代码停掉
                    if (j==49){
                        invoker.stop();
                        throw new TimeLimitExceedException("超出时间限制");
                    }
                }
            }
        } catch (NoSuchMethodException | IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Method getMethod(String name,Class<?> clazz) throws NoSuchMethodException {
        clazz.getDeclaredConstructor().setAccessible(true);
        Method[] methods = clazz.getMethods();
        for (Method method1 : methods) {
            if (method1.getName().equals(name)){
                method1.setAccessible(true);
                return method1;
            }
        }
        return null;
    }

    private <T> Object convert(String obj,Class<T> clazz){
        if (clazz==Integer.class || clazz==int.class){
            return Integer.valueOf(obj);
        }
        return obj;
    }

    private static class Invoker extends Thread{
        private final ResultBox box;
        Method method;
        Object[] params;

        public Invoker(ResultBox box, Method method, Object[] params) {
            this.box = box;
            this.method = method;
            this.params = params;
        }

        @Override
        public void run() {
            // 因为是静态方法，所以传入的obj为null。
            try {
                box.result= method.invoke(null, params);
            } catch (Exception e) {
                e.printStackTrace();
                box.exception = e;
            }
        }
    }
}
