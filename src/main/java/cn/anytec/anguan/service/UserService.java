package cn.anytec.anguan.service;

import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.po.Role;
import cn.anytec.anguan.po.User;
import cn.anytec.anguan.repository.UserRepository;
import cn.anytec.anguan.service.inf.IUser;
import cn.anytec.anguan.util.Constant;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imyzt on 2018/9/26 9:57
 */
@Service
public class UserService implements IUser<Integer, User, String, Page<User>>{

    @Autowired
    private UserRepository userRepository;

    private final Log log = LogFactory.get();

    @Override
    public void del(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e);
            throw new AnguanException("删除失败");
        }
    }

    @Override
    public User edit(User data) {
        User user = findById(data.getId());
        data.setStatus(null);
        copyNullProperties(user, data);
        return userRepository.save(data);
    }

    @Override
    public User update(User data) {
        User user = findById(data.getId());
        data.setStatus(user.getStatus());
        data.setUpass(user.getUpass());
        try {
            return edit(data);
        } catch (Exception e) {
            log.error(e);
            throw new AnguanException("修改失败,请重试");
        }
    }

    /**
     * 用户注册
     */
    @Override
    public User save(User data) {

        boolean checkUname = checkUname(data.getUname());
        // 用户名不存在
        if (checkUname) {

            data.setStatus(Constant.UserStatus.ENABLE);
            data.setUpass(new Md5Hash(data.getUpass(), Constant.ME.SALT).toHex());

            return userRepository.save(data);
        }
        throw new AnguanException("用户名已存在");
    }

    private boolean checkUname(String uname) {
        User user = userRepository.findByUname(uname);
        return user == null;
    }


    @Override
    public List<User> findAllByUname(String uname) {
        return userRepository.findAllByUnameLike("%" + uname + "%");
    }

    @Override
    public User findById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public Page<User> list(Integer pageNum, Integer pageSize, String uname) {

        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {

            ArrayList<Predicate> predicates = new ArrayList<>();
            if (StrUtil.isNotBlank(uname)) {
                predicates.add(criteriaBuilder.like(root.get("uname"), "%" + uname + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return userRepository.findAll(specification, PageRequest.of(pageNum, pageSize));
    }

    @Override
    public List<Role> findRoleByUserId(Integer uid) {
        User byId = findById(uid);
        return byId.getRoleList();
    }
}
