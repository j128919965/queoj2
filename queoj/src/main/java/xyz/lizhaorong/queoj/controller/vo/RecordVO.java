package xyz.lizhaorong.queoj.controller.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RecordVO {
    @NotNull
    private Integer uid;
    @NotNull
    private Integer pid;
    @NotBlank
    private String code;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
