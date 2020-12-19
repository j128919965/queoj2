package xyz.lizhaorong.queoj.entity;

import javax.persistence.*;

@Table(name = "problem")
public class Problem {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private Integer point;

    private Byte level;

    @Column(name = "default_code")
    private String defaultCode;

    @Column(name = "pending_code")
    private String pendingCode;

    private String description;

    private String codesig;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return point
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return level
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * @return default_code
     */
    public String getDefaultCode() {
        return defaultCode;
    }

    /**
     * @param defaultCode
     */
    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    /**
     * @return pending_code
     */
    public String getPendingCode() {
        return pendingCode;
    }

    /**
     * @param pendingCode
     */
    public void setPendingCode(String pendingCode) {
        this.pendingCode = pendingCode;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodesig() {
        return codesig;
    }

    public void setCodesig(String codesig) {
        this.codesig = codesig;
    }
}