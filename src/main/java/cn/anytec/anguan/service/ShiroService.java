package cn.anytec.anguan.service;

import cn.anytec.anguan.core.exception.AnguanException;
import cn.anytec.anguan.core.realm.AnguanRealm;
import cn.anytec.anguan.po.Permission;
import cn.anytec.anguan.repository.PermissionRepository;
import cn.anytec.anguan.repository.RoleRepository;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by imyzt on 2018/9/26 18:40
 * 用于对shiro的操作
 */
@Service
public class ShiroService {

    @Autowired
    private ShiroFilterFactoryBean shiroFilter;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;

    private final Log log = LogFactory.get();

    public LinkedHashMap initPermission() {

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        // 从数据库获取资源树
        Iterable<Permission> permissions = permissionRepository.findAll();
        permissions.forEach(permission -> {
            if (StrUtil.isNotBlank(permission.getType())) {
                String perms = "perms[" + permission.getType() + "]";
                linkedHashMap.put(permission.getType(), perms);
            }
        });

        return linkedHashMap;
    }

    /**
     * 不重启更新权限
     */
    public void updatePermission() {

        synchronized(shiroFilter) {

            AbstractShiroFilter abstractShiroFilter = null;
            try {
                abstractShiroFilter = (AbstractShiroFilter) shiroFilter.getObject();
            } catch (Exception e) {
                log.error(e);
                throw new AnguanException("系统异常");
            }

            PathMatchingFilterChainResolver pathMatchingFilterChainResolver =
                    (PathMatchingFilterChainResolver) abstractShiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager =
                    (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();

            // 清空老权限
            manager.getFilterChains().clear();
            shiroFilter.getFilterChainDefinitionMap().clear();

            shiroFilter.setFilterChainDefinitionMap(initPermission());

            // 重新构建生成
            Map<String, String> chains = shiroFilter.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

             log.info("权限更新成功");
        }

    }
}
