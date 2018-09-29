package cn.anytec.anguan.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by imyzt on 2018/9/28 14:15
 */
@Entity
@Table(name = "sys_role_permission")
public class RolePermission implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer rid;

    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public RolePermission() {
    }

    public RolePermission(Integer rid, Integer pid) {
        this.rid = rid;
        this.pid = pid;
    }
}
