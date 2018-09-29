package cn.anytec.anguan.core.realm;

import cn.anytec.anguan.po.User;
import cn.anytec.anguan.repository.UserRepository;
import cn.anytec.anguan.util.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by imyzt on 2018/9/26 11:40
 */
@Component
public class AnguanRealm extends AuthorizingRealm{

    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 从session中获取
        user.getRoleList().forEach(role -> {
            authorizationInfo.addRole(role.getType());
            role.getPermissionList().
                    forEach(permission ->
                            authorizationInfo.addStringPermission(permission.getType()));
        });
        return authorizationInfo;
    }

    /**
     * 认证(登录)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)  {

        String uname = (String) authenticationToken.getPrincipal();

        User user = userRepository.findByUname(uname);

        if (null == user) {
            throw new UnknownAccountException("用户不存在");
        }

        Integer status = user.getStatus();

        if (status == null || Constant.UserStatus.DELETED.equals(status) || Constant.UserStatus.DISABLE.equals(status)) {
            throw new LockedAccountException("账号已被禁用");
        }

        return new SimpleAuthenticationInfo(
                user,
                user.getUpass(),
                ByteSource.Util.bytes(Constant.ME.SALT),
                getName()
        );
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("1", ByteSource.Util.bytes(Constant.ME.SALT));
        System.out.println(md5Hash.toHex());
    }
}
