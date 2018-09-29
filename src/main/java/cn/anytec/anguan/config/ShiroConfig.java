package cn.anytec.anguan.config;

import cn.anytec.anguan.core.realm.AnguanRealm;
import cn.anytec.anguan.po.Permission;
import cn.anytec.anguan.repository.PermissionRepository;
import cn.hutool.core.util.StrUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * Created by imyzt on 2018/9/26 11:29
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private PermissionRepository permissionRepository;
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer shiroPort;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        shiroFilter.setSecurityManager(securityManager);

        // 设置登录的url
        shiroFilter.setLoginUrl("/user/unauth");
        // 登录成功后跳转的请求
//        shiroFilter.setSuccessUrl("/index");
        // 未授权的请求
//        shiroFilter.setUnauthorizedUrl("/403");

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 配置不被拦截的链接
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon"); // 表示可以匿名访问
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/swagger-*/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        filterChainDefinitionMap.put("/**", "authc");

        // 从数据库获取资源树
        Iterable<Permission> permissions = permissionRepository.findAll();
        permissions.forEach(permission -> {
            if (StrUtil.isNotBlank(permission.getType())) {
                String perms = "perms[" + permission.getType() + "]";
                filterChainDefinitionMap.put(permission.getType(), perms);
            }
        });

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        System.out.println("shiro拦截器工厂类注入成功");

        return shiroFilter;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        return hashedCredentialsMatcher;
    }

    @Bean
    public Realm AnguanRealm() {
        AnguanRealm anguanRealm = new AnguanRealm();
        anguanRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return anguanRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(AnguanRealm());
        // 自定义缓存实现,使用redis
        defaultWebSecurityManager.setCacheManager(redisCacheManager());
        // 自定义session实现,使用redis
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 为缓存和session实现配置的redis实例
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost);
        redisManager.setPort(shiroPort);
        redisManager.setExpire(1800);
        return redisManager;
    }

    /**
     * redis缓存实现
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 为session实现配置的redisDAO操作
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 自定义session实现
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        return defaultWebSessionManager;
    }

}
