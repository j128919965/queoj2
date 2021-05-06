package xyz.lizhaorong.queoj.core.util;

import jdk.internal.perf.PerfCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.lizhaorong.queoj.core.exception.CompileWrongException;
import xyz.lizhaorong.queoj.entity.Record;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

@Slf4j
@Component
public class OjClassLoader extends ClassLoader{

    private Record record;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        String libPath = OjPathManager.getDst(record);

        File file = new File(libPath, fileName);

        try {
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            try {
                while ((len = is.read()) != -1) {
                    bos.write(len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = bos.toByteArray();
            is.close();
            bos.close();
            return defineClass(name, data, 0, data.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 获取要加载 的class文件名
     *
     * @param name String
     * @return String
     */
    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        } else {
            return name.substring(index + 1) + ".class";
        }
    }

    /**
     * 为record的保持线程安全
     * @param record
     * @return
     * @throws CompileWrongException
     */
    synchronized public Class<?> load(Record record) throws CompileWrongException{
        try{
            this.record = record;
            return this.loadClass("Solution");
        }catch (ClassNotFoundException e){
            log.error("类加载失败，没有找到文件");
            CompileWrongException exception = new CompileWrongException("类加载失败：没有找到文件");
            exception.initCause(e);
            throw exception;
        }
    }


}
