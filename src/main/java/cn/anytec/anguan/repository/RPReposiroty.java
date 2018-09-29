package cn.anytec.anguan.repository;

import cn.anytec.anguan.po.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by imyzt on 2018/9/28 14:19
 */
public interface RPReposiroty  extends JpaRepository<RolePermission, Integer>, JpaSpecificationExecutor<RolePermission> {

    void deleteRolePermissionsByRid(Integer rid);

    void deleteRolePermissionByPid(Integer pid);

    void deleteByRid(Integer rid);

}
