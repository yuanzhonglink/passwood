package com.ldap.config;

import com.ldap.util.AuthenticationFailHandler;
import com.ldap.util.AuthenticationSuccessHandler;
import com.ldap.util.LDAPAuthenticationProvider;
import com.ldap.util.LdapProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticator;

/**
 * Security 核心配置类
 * 开启控制权限至Controller
 *
 * @author Exrickx
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LdapProperties ldapProperties;

    @Autowired
    private LDAPAuthenticationProvider ldapAuthenticationProvider;

    @Autowired
    private AuthenticationSuccessHandler  successHandler;

    @Autowired
    private AuthenticationFailHandler failHandler;

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
            .authorizeRequests();

        registry.and()
                //表单登录方式
                .formLogin()
                //登录需要经过的url请求
                .loginProcessingUrl("/ldap/login")
                .permitAll()
                //成功处理类
                .successHandler(successHandler)
                //失败
                .failureHandler(failHandler)
                .and()
                .logout()
                .permitAll()
                .and()
                //关闭跨站请求防护
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ldapAuthenticationProvider);
    }

    @Bean
    public LdapAuthenticator ldapAuthenticator() {
        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(
            ldapProperties.getServer() + "/" + ldapProperties.getRoot_dn());
        contextSource.setReferral("follow");
        contextSource.setCacheEnvironmentProperties(true);
        contextSource.setAnonymousReadOnly(false);
        contextSource.setPooled(true);
        contextSource.setUserDn(ldapProperties.getRoot_user());
        contextSource.setPassword(ldapProperties.getRoot_password());
        contextSource.afterPropertiesSet();
        //FilterBasedLdapUserSearch ldapUserSearch = new FilterBasedLdapUserSearch(ldapProperties.getUser_search_base(), ldapProperties.getUser_search_filter(),contextSource);
        //ldapUserSearch.setSearchSubtree(true);
        BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource);
        //bindAuthenticator.setUserSearch(ldapUserSearch);
        bindAuthenticator.setUserDnPatterns(ldapProperties.getPattern());
        return bindAuthenticator;
    }

    @Bean
    public LDAPAuthenticationProvider ldapAuthenticationProvider(LdapAuthenticator ldapAuthenticator) {
        return new LDAPAuthenticationProvider(ldapAuthenticator);
    }

}
