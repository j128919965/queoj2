package xyz.lizhaorong.queoj.entity;

import java.util.Date;

public class Record {
    private Integer id;

    private Integer uid;

    private Integer pid;

    /**
     * 1 ac
        2 pending
        3 wa
        4 re
        5 ce
        6 tle

     */
    private Byte state;

    private Date time;

    private Long usedTime;

    private Long usedSpace;

    private String code;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return used_time
     */
    public Long getUsedTime() {
        return usedTime;
    }

    /**
     * @param usedTime
     */
    public void setUsedTime(Long usedTime) {
        this.usedTime = usedTime;
    }

    /**
     * @return used_space
     */
    public Long getUsedSpace() {
        return usedSpace;
    }

    /**
     * @param usedSpace
     */
    public void setUsedSpace(Long usedSpace) {
        this.usedSpace = usedSpace;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", uid=" + uid +
                ", pid=" + pid +
                ", state=" + state +
                ", time=" + time +
                ", usedTime=" + usedTime +
                ", usedSpace=" + usedSpace +
                ", code='" + code + '\'' +
                '}';
    }
}