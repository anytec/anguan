package cn.anytec.anguan.service;

import cn.anytec.anguan.component.facedetect.model.vo.PermissionsVO;
import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.po.Permission;
import cn.anytec.anguan.po.Role;
import cn.anytec.anguan.po.RolePermission;
import cn.anytec.anguan.repository.PermissionRepository;
import cn.anytec.anguan.repository.RPReposiroty;
import cn.anytec.anguan.service.inf.IPermission;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by imyzt on 2018/9/27 17:05
 */
@Service
@Transactional
public class PermissionService implements IPermission<Integer, Permission, String, Page<Permission>> {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RPReposiroty rpReposiroty;
    @Autowired
    private RoleService roleService;

    @Override
    public void addPermissions2Role(Integer rid, String permissions) {

        Assert.isTrue(StrUtil.isNotBlank(permissions), "权限信息不能为空");

        String[] pids = permissions.split(",");

        Role role = roleService.findById(rid);
        if (role == null) {
            throw new AnguanException("角色不存在");
        }

        // 删除角色所有权限
        rpReposiroty.deleteByRid(rid);

        List<Permission> allPermissions = permissionRepository.findAll();
        Set<Integer> permissionIds = allPermissions.stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());

        Set<RolePermission> permissionSet = new LinkedHashSet<>();
        for (String pid : pids) {

            Integer value;
            try {
                value = Integer.valueOf(pid);
            } catch (NumberFormatException e) {
                throw new AnguanException("权限信息格式错误");
            }

            if (!permissionIds.contains(value)) {
                throw new AnguanException("权限不存在");
            }

            RolePermission rolePermission = new RolePermission(rid, value);
            permissionSet.add(rolePermission);
        }

        rpReposiroty.saveAll(permissionSet);
    }

    @Override
    public List<PermissionsVO> rolePermissions(Integer pageNum, Integer pageSize, String rname) {


        Page<Role> page = roleService.list(pageNum, pageSize, rname);
        List<PermissionsVO> permissionsVOList = new ArrayList<>();

        if (page.getTotalElements() > 0) {

            List<Role> roles = page.getContent();

            roles.forEach(role -> {

                PermissionsVO permissionsVO = new PermissionsVO();

                List<Permission> permissionList = role.getPermissionList();
                StringBuilder sb = new StringBuilder(10);

                if (CollectionUtil.isNotEmpty(permissionList)) {
                    permissionList.forEach(permission -> sb.append(",").append(permission.getName()));
                }

                permissionsVO.setRoleId(role.getId());
                permissionsVO.setRoleName(role.getName());
                permissionsVO.setRoleType(role.getType());
                if (StrUtil.isNotBlank(sb)) {
                    permissionsVO.setHasPermissions(sb.substring(1));
                }
                permissionsVOList.add(permissionsVO);
            });
        }

        return permissionsVOList;
    }

    @Override
    public Page list(Integer pageNum, Integer pageSize, String name) {

        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            ArrayList<Predicate> predicates = new ArrayList<>();
            if (StrUtil.isNotBlank(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return permissionRepository.findAll(specification, PageRequest.of(pageNum, pageSize));
    }

    @Override
    public void del(Integer id) {
        Optional<Permission> byId = permissionRepository.findById(id);
        if (byId.isPresent()) {

            // 删除权限是删除与该权限有关的角色对应关系
            rpReposiroty.deleteRolePermissionByPid(id);
            // 删除权限
            permissionRepository.deleteById(id);
        }
    }

    @Override
    public Permission edit(Permission data) {

        Permission byId = findById(data.getId());
        copyNullProperties(byId, data);
        return permissionRepository.save(data);
    }

    @Override
    public Permission update(Permission data) {
        return null;
    }

    @Override
    public Permission save(Permission data) {
        return permissionRepository.save(data);
    }

    @Override
    public Permission findById(Integer id) {

        Optional<Permission> byId = permissionRepository.findById(id);
        return byId.orElse(null);
    }

}
