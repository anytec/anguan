package cn.anytec.anguan.repository;

import cn.anytec.anguan.po.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by imyzt on 2018/9/28 14:18
 */
public interface URRepository extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findByUid(Integer uid);

    List<UserRole> findByRid(Integer rid);

    void deleteUserRolesByRid(Integer rid);

    void deleteByUid(Integer uid);

}
