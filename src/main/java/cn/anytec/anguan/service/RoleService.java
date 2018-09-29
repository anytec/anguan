package cn.anytec.anguan.service;

import cn.anytec.anguan.component.facedetect.model.vo.RolesVO;
import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.po.Permission;
import cn.anytec.anguan.po.Role;
import cn.anytec.anguan.po.User;
import cn.anytec.anguan.po.UserRole;
import cn.anytec.anguan.repository.RoleRepository;
import cn.anytec.anguan.repository.URRepository;
import cn.anytec.anguan.service.inf.IRole;
import cn.anytec.anguan.service.inf.IUser;
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
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by imyzt on 2018/9/27 17:04
 */
@Service
@Transactional
public class RoleService implements IRole<Integer,Role,String,Page<Role>> {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private URRepository urRepository;
    @Autowired
    private UserService userSerivce;

    @Override
    public void addRoles2User(Integer uid, String roles) {

        Assert.isTrue(StrUtil.isNotBlank(roles), "角色信息不能为空");

        String[] rids = roles.split(",");

        User user = userSerivce.findById(uid);
        if (user == null) {
            throw new AnguanException("用户不存在");
        }

        // 删除用户所有角色
        urRepository.deleteByUid(uid);

        List<Role> allRoles = roleRepository.findAll();
        Set<Integer> roleIds = allRoles.stream().map(Role::getId).collect(Collectors.toSet());

        Set<UserRole> roleSet = new LinkedHashSet<>();
        // 添加新角色
        for (String rid : rids) {
            Integer value;
            try {
                value = Integer.valueOf(rid);
            } catch (NumberFormatException e) {
                throw new AnguanException("角色信息格式错误");
            }

            if (!roleIds.contains(value)) {
                throw new AnguanException("角色不存在");
            }

            UserRole userRole = new UserRole(uid, value);
            roleSet.add(userRole);
        }

        urRepository.saveAll(roleSet);
    }

    @Override
    public void clearRoles2User(Integer uid) {
        urRepository.deleteByUid(uid);
    }

    @Override
    public List<Permission> findPermissionsByRoleId(Integer rid) {

        Role byId = findById(rid);
        return byId.getPermissionList();
    }

    @Override
    public List<RolesVO> usersRoles(Integer pageNum, Integer pageSize, String uname) {

        Page<User> page = userSerivce.list(pageNum, pageSize, uname);
        ArrayList<RolesVO> userRoles = new ArrayList<>();

        if (page.getTotalElements() > 0) {

            List<User> users = page.getContent();

            users.forEach(user -> {
                RolesVO rolesVO = new RolesVO();

                List<Role> userRoleList = user.getRoleList();
                StringBuilder sb = new StringBuilder(5);
                if (CollectionUtil.isNotEmpty(userRoleList)) {
                    userRoleList.forEach(role -> sb.append(",").append(role.getName()));
                }

                rolesVO.setUid(user.getId());
                rolesVO.setUname(user.getUname());
                rolesVO.setUserStatus(user.getStatus());
                if (StrUtil.isNotBlank(sb)) {
                    rolesVO.setHasRoles(sb.substring(1));
                }
                userRoles.add(rolesVO);
            });

        }

        return userRoles;
    }

    @Override
    public void del(Integer id) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()) {

            // 删除角色
            // (此操作由JPA主动维护)删除角色时删除与该角色有关的权限对应关系
            roleRepository.deleteById(id);
            // 删除角色时删除与该角色有关的用户对应关系
            urRepository.deleteUserRolesByRid(id);
        }
    }

    @Override
    public Role edit(Role data) {

        Role byId = findById(data.getId());
        copyNullProperties(byId, data);
        return roleRepository.save(data);
    }

    @Override
    public Role update(Role data) {
        return roleRepository.save(data);
    }

    @Override
    public Role save(Role data) {
        return roleRepository.save(data);
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Role> list(Integer pageNum, Integer pageSize, String query) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            ArrayList<Predicate> predicates = new ArrayList<>();
            if (StrUtil.isNotBlank(query)) {
                predicates.add(criteriaBuilder.like(root.get("name"), query + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return roleRepository.findAll(specification, PageRequest.of(pageNum, pageSize));
    }
}
