package xyz.lizhaorong.queoj.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.lizhaorong.queoj.core.manager.RecordManager;
import xyz.lizhaorong.queoj.entity.Record;

@Component
public class MainRunner implements CommandLineRunner {

    @Autowired
    RecordManager recordManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start");
        Record record = recordManager.getRecord();
        System.out.println(record);
    }
}
