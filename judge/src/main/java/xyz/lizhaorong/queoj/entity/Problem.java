package xyz.lizhaorong.queoj.entity;


public class Problem {
    private Integer id;

    private String name;

    private Integer point;

    private Byte level;

    private String defaultCode;

    private String pendingCode;

    private String description;

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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    public String getPendingCode() {
        return pendingCode;
    }

    public void setPendingCode(String pendingCode) {
        this.pendingCode = pendingCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", point=" + point +
                ", level=" + level +
                ", defaultCode='" + defaultCode + '\'' +
                ", pendingCode='" + pendingCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}