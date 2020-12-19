package xyz.lizhaorong.queoj.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.lizhaorong.queoj.core.exception.CompileWrongException;
import xyz.lizhaorong.queoj.entity.Record;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Component
public class OjCompiler {

    private static final JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

    public void compile(Record record) throws CompileWrongException {
        String src = OjPathManager.getSrc(record);
        String dst = OjPathManager.getDst(record);

        try {
            File file = new File(src);
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(record.getCode());
            writer.flush();
        } catch (IOException e) {
            log.error("编译前写入失败");
            CompileWrongException exception = new CompileWrongException("编译前写入失败");
            exception.initCause(e);
            throw exception;
        }

        try {
            //编译
            int status = javac.run(null, null, null, "-d", dst, src);
            if (status != 0) {
                throw new CompileWrongException("编译错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CompileWrongException("编译错误");
        }
    }

}
