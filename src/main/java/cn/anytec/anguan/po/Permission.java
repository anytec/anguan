package cn.anytec.anguan.po;

import com.sun.corba.se.spi.ior.Identifiable;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by imyzt on 2018/9/26 11:12
 */
@Entity
@Table(name = "sys_permission")
@DynamicUpdate
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 权限类型 */
    private String type;

    /** 权限名称 */
    private String name;

    /** 权限-角色对应关系 */
    /*@ManyToMany
    @JoinTable(name = "sys_role_permission",
            joinColumns = {@JoinColumn(name = "pid")},
            inverseJoinColumns = {@JoinColumn(name = "rid")})
    private List<Role> roleList;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }*/

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
