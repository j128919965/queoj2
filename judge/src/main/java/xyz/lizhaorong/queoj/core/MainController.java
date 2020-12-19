package xyz.lizhaorong.queoj.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.lizhaorong.queoj.core.manager.RecordManager;
import xyz.lizhaorong.queoj.core.util.OJSecurityManager;
import xyz.lizhaorong.queoj.core.util.OjClassLoader;
import xyz.lizhaorong.queoj.core.util.OjCompiler;
import xyz.lizhaorong.queoj.core.util.OjRunner;
import xyz.lizhaorong.queoj.entity.Record;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MainController implements CommandLineRunner {

    private static final AtomicInteger count = new AtomicInteger();

    private static final int MAX_THREAD_SIZE = 3;

    @Autowired
    OjCompiler compiler;

    // 此处如果使用同一个classLoader对象，会因为类缓存的原因，无法重新加载
    //@Autowired
    //OjClassLoader classLoader;

    @Autowired
    OjRunner runner;

    /**
     * 限制最大同时判题线程数
     */
    private static final Semaphore lock = new Semaphore(MAX_THREAD_SIZE);

    @Autowired
    RecordManager recordManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start");
        System.setSecurityManager(new OJSecurityManager());
        while (true){
            Record record = recordManager.getRecord();
            if (record==null)continue;
            lock.acquire();
            new MainRunner(record,compiler,runner,recordManager).start();
        }
    }

    public static void startAWork(){
        count.incrementAndGet();
    }

    public static void finishAWork(){
        count.decrementAndGet();
        lock.release();
    }
}
