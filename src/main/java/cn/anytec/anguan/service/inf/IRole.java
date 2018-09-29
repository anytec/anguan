package cn.anytec.anguan.service.inf;

import cn.anytec.anguan.component.facedetect.model.vo.RolesVO;
import cn.anytec.anguan.po.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imyzt on 2018/9/27 17:04
 */
public interface IRole<I, T extends Serializable, Q, P> extends BaseService<I, T, Q, P> {

    /**
     * 获取角色拥有的所有权限
     * @param rid 角色id
     */
    List<Permission> findPermissionsByRoleId(Integer rid);

    /**
     * 给用户添加角色
     * @param uid 用户id
     * @param roles 角色id列表
     */
    void addRoles2User(Integer uid, String roles);

    /**
     * 删除用户的所有角色
     * @param uid 用户id
     */
    void clearRoles2User(Integer uid);

    List<RolesVO> usersRoles(Integer pageNum, Integer pageSize, String uname);
}