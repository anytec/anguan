package cn.anytec.anguan.component.facedetect.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by imyzt on 2018/9/29 11:00
 * 角色-用户
 */
public class RolesVO implements Serializable {

    @JsonProperty("id")
    private Integer uid;

    @JsonProperty("name")
    private String uname;

    @JsonProperty("status")
    private Integer userStatus;

    @JsonProperty("roles")
    private String hasRoles;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getHasRoles() {
        return hasRoles;
    }

    public void setHasRoles(String hasRoles) {
        this.hasRoles = hasRoles;
    }
}
