package cn.anytec.anguan.repository;

import cn.anytec.anguan.po.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by imyzt on 2018/9/26 11:27
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer>, JpaSpecificationExecutor<Permission> {

    Permission findByNameLike(String name);

}
