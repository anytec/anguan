package cn.anytec.anguan.repository;

import cn.anytec.anguan.po.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by imyzt on 2018/9/26 9:55
 * 用户管理
 */
@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

    User findByUname(String uname);

    List<User> findAllByUnameLike(String uname);
}
