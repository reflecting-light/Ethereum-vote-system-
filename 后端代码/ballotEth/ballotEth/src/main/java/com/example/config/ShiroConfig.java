package com.example.config;

import com.example.shiro.realms.LoginRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
//    1.创建shiroFilter拦截器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        Map<String,String> map= new HashMap<String,String>();
        //        配置公共资源
//        map.put("/","anon");
        map.put("/user/**","anon");   //注意：若受限资源设为“/**”，那么，公共资源的设置 要在 受限资源 的 前面！！！
        //        配置受限资源
//        map.put("/user/**","authc"); //注意：是“/index.jsp”，不要忘记“/”，很重要，关系到能否成功拦截！！！     //authc表示请求该资源需要认证和授权
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

//        默认认证页面路径
        shiroFilterFactoryBean.setLoginUrl("/user/loginByUsername");
        return shiroFilterFactoryBean;
    }

//    2.创建安全管理器
    @Bean
    public  DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

//    创建自定义的Realm
    @Bean
    public Realm getRealm(){
        LoginRealm loginRealm = new LoginRealm();
//        修改凭证校验匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);

        loginRealm.setCredentialsMatcher(hashedCredentialsMatcher);

//        开启缓存管理
        loginRealm.setCacheManager(new EhCacheManager());

//        开启全局缓存
        loginRealm.setCachingEnabled(true);
//        开启认证缓存,此操作必须放在 开启全局缓存  之后
        loginRealm.setAuthenticationCachingEnabled(true);
//        设置认证缓存的名字
        loginRealm.setAuthenticationCacheName("authenticationCache");
//        开启授权缓存，此操作与 开启认证缓存 同理
        loginRealm.setAuthorizationCachingEnabled(true);
//        设置授权缓存的名字
        loginRealm.setAuthorizationCacheName("authorizationCache");


        return loginRealm;
    }
}
