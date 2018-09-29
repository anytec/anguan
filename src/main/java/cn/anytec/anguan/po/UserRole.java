package cn.anytec.anguan.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by imyzt on 2018/9/28 14:15
 */
@Entity
@Table(name = "sys_user_role")
public class UserRole implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid;

    private Integer rid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public UserRole() {
    }

    public UserRole(Integer uid, Integer rid) {
        this.uid = uid;
        this.rid = rid;
    }
}
