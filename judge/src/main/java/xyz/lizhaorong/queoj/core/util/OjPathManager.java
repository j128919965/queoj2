package xyz.lizhaorong.queoj.core.util;

import xyz.lizhaorong.queoj.entity.Record;

public class OjPathManager {

    private static final String SRC_PREFIX = "E://queoj//src//";
    private static  final String DST_PREFIX = "E://queoj//cls//";


    public static String getSrc(Record record) {
        return SRC_PREFIX + record.getPid() + "//" +record.getUid() + "//" + record.getTime().getTime()+"//Solution.java";
    }

    public static String getDst(Record record){
        return DST_PREFIX + record.getPid() + "//" +record.getUid() + "//" + record.getTime().getTime();
    }
}
