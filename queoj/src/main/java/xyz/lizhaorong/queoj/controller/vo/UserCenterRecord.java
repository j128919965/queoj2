package xyz.lizhaorong.queoj.controller.vo;

import java.time.LocalDate;

public class UserCenterRecord {
    private Integer id;
    private String name;
    private Integer state;
    private LocalDate time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
