package cn.anytec.anguan.repository;

import cn.anytec.anguan.po.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by imyzt on 2018/9/26 11:26
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    Role findByNameLike(String name);

}
