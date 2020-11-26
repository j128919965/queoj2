package xyz.lizhaorong.queoj.entity;

import javax.persistence.*;

@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    private String favicon;

    private Byte gender;

    private String phone;

    private String email;

    private Integer point;

    private String introduction;

    private String github;

    private String website;

    private Integer role;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

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
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return nickName
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return favicon
     */
    public String getFavicon() {
        return favicon;
    }

    /**
     * @param favicon
     */
    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    /**
     * @return gender
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * @return github
     */
    public String getGithub() {
        return github;
    }

    /**
     * @param github
     */
    public void setGithub(String github) {
        this.github = github;
    }

    /**
     * @return website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     */
    public void setWebsite(String website) {
        this.website = website;
    }
}