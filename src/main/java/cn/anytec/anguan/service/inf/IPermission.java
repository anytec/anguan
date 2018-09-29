package cn.anytec.anguan.service.inf;

import cn.anytec.anguan.component.facedetect.model.vo.PermissionsVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imyzt on 2018/9/27 17:04
 */
public interface IPermission<I, T extends Serializable, Q, P> extends BaseService<I, T, Q, P> {

    void addPermissions2Role(Integer rid, String permissions);

    /**
     * 展示角色的所有权限
     * @param pageNum 分页信息
     * @param pageSize 分页信息
     * @param rname 角色名称
     */
    List<PermissionsVO> rolePermissions(Integer pageNum, Integer pageSize, String rname);
}
