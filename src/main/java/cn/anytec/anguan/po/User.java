package cn.anytec.anguan.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by imyzt on 2018/9/26 9:50
 * 系统用户表
 */
@Entity
@Table(name = "sys_user")
public class User implements Serializable{

    /** 用户编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 用户头像 */
    private String avatar;

    /** 用户姓名 */
    private String uname;

    /** 用户密码 */
    @JsonIgnore
    private String upass;

    /** 联系方式(手机号/邮箱地址) */
    private String contact;

    /** 用户状态(0-禁用,1-启用;default=1) */
    private Integer status;

    /** 备注信息 */
    private String notes;

    /** 一个用户具有多个角色 */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)                        // 立即从数据库获取更新
    @JoinTable(name = "sys_user_role",                          // 关系表表名称
            joinColumns = {@JoinColumn(name = "uid")},          // 当前对象的关联主键
            inverseJoinColumns = {@JoinColumn(name = "rid")})   // 关联对象的关联主键
    private List<Role> roleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", uname='" + uname + '\'' +
                ", upass='" + upass + '\'' +
                ", contact='" + contact + '\'' +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }
}
