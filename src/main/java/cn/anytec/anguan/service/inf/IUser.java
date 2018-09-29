package cn.anytec.anguan.service.inf;

import cn.anytec.anguan.po.Role;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imyzt on 2018/9/26 9:57
 * 用户管理
 */
public interface IUser<I, T extends Serializable, Q, P> extends BaseService<I, T, Q, P> {

    /**
     * 获取用户拥有的所有角色
     * @param uid 用户id
     */
    List<Role> findRoleByUserId(Integer uid);

    List<T> findAllByUname(String uname);
}
