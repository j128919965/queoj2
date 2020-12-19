package xyz.lizhaorong.queoj.core;

import lombok.extern.slf4j.Slf4j;
import xyz.lizhaorong.queoj.core.exception.CompileWrongException;
import xyz.lizhaorong.queoj.core.exception.TimeLimitExceedException;
import xyz.lizhaorong.queoj.core.exception.WrongAnswerException;
import xyz.lizhaorong.queoj.core.manager.RecordManager;
import xyz.lizhaorong.queoj.core.util.OjClassLoader;
import xyz.lizhaorong.queoj.core.util.OjCompiler;
import xyz.lizhaorong.queoj.core.util.OjRunner;
import xyz.lizhaorong.queoj.entity.Record;

import java.io.IOException;


@Slf4j
public class MainRunner extends Thread{

    private final Record record;

    private OjCompiler compiler;

    private OjClassLoader classLoader;

    private OjRunner runner;

    private RecordManager recordManager;

    public MainRunner(Record record, OjCompiler compiler, OjRunner runner,RecordManager recordManager) {
        this.record = record;
        this.compiler = compiler;
        this.classLoader = new OjClassLoader();
        this.recordManager = recordManager;
        this.runner = runner;
    }

    @Override
    public void run(){
        MainController.startAWork();
        record.setUsedSpace(-1L);
        record.setUsedTime(-1L);
        log.debug("start pending ...");
        try{
            compiler.compile(record);
            log.debug("compile success");
            Class<?> clazz = classLoader.load(record);
            log.debug("load success");
            runner.run(record,clazz);
            log.debug("run success");
            // ACCEPT
            record.setState((byte)1);
        }catch (CompileWrongException exception){
            // CE
            record.setState((byte)5);
            log.debug("ce");
        }catch (WrongAnswerException exception){
            // WA
            record.setState((byte)3);
            log.debug("wa");
        }catch (TimeLimitExceedException exception){
            // TLE
            record.setState((byte)6);
            log.debug("tle");
        }catch (Exception e){
            // RE
            record.setState((byte)4);
            log.debug("re");
            e.printStackTrace();
        }finally {
            log.debug("end pending ...");
            recordManager.saveRecord(record);
            MainController.finishAWork();
        }
    }

}
