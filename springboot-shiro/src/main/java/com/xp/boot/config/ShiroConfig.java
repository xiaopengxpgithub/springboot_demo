package com.xp.boot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.xp.boot.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "shiro",ignoreUnknownFields = true)  //匹配springboot配置文件中的属性
public class ShiroConfig {

    private String hashAlgorithmName="md5";
    private int hashIterations=2;
    private String loginUrl = "/index.html"; // 登陆页面
    private String unauthorizedUrl = "/unauthorize.html"; // 未授权的页面

    private String[] anonUrls; // 放行的url
    private String logoutUrl;// 登出的URL
    private String[] authcUrls;// 需要认证的url

    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }

    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String[] getAnonUrls() {
        return anonUrls;
    }

    public void setAnonUrls(String[] anonUrls) {
        this.anonUrls = anonUrls;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String[] getAuthcUrls() {
        return authcUrls;
    }

    public void setAuthcUrls(String[] authcUrls) {
        this.authcUrls = authcUrls;
    }

    /**
     * 声明凭证匹配器
     */
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 注入加密方式
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        // 注入散列次数
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }

    /**
     * 声明Realm
     */
    @Bean
    public UserRealm getUserRealm(HashedCredentialsMatcher credentialsMatcher) {
        UserRealm realm = new UserRealm();
        // 注入凭证匹配器
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    /**
     * 声明securityManager
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * Shiro 的Web过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口
        factoryBean.setSecurityManager(securityManager);
        // 注入未登陆的跳转页面
        factoryBean.setLoginUrl(loginUrl);
        // 注入未授权的跳转页面
        factoryBean.setUnauthorizedUrl(unauthorizedUrl);
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        if (this.checkArrayIsEmpty(anonUrls)) {
            for (String url : anonUrls) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }
        filterChainDefinitionMap.put(logoutUrl, "logout");
        if (this.checkArrayIsEmpty(authcUrls)) {
            for (String url : authcUrls) {
                filterChainDefinitionMap.put(url, "authc");
            }
        }
        // 注入过滤器链
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;

    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注入委托过滤器
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> getDelegatingFilterProxy() {
        DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
        filterProxy.setTargetFilterLifecycle(true);
        filterProxy.setTargetBeanName("shiroFilter");

        FilterRegistrationBean<DelegatingFilterProxy> bean = new FilterRegistrationBean<>();

        bean.setFilter(filterProxy);
        return bean;
    }

    // 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 检查数组是否为空
     *
     * @param arrays
     * @return
     */
    //@RequiresRoles("超级管理员")
    public boolean checkArrayIsEmpty(String[] arrays) {
        if (this.anonUrls != null && this.anonUrls.length > 0) {
            return true;
        } else {
            return false;
        }
    }
}
