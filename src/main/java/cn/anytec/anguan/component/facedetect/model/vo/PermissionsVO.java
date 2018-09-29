package cn.anytec.anguan.component.facedetect.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by imyzt on 2018/9/29 14:56
 */
public class PermissionsVO implements Serializable {

    @JsonProperty("id")
    private Integer roleId;

    @JsonProperty("name")
    private String roleName;

    @JsonProperty("type")
    private String roleType;

    @JsonProperty("permissions")
    private String hasPermissions;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getHasPermissions() {
        return hasPermissions;
    }

    public void setHasPermissions(String hasPermissions) {
        this.hasPermissions = hasPermissions;
    }
}
