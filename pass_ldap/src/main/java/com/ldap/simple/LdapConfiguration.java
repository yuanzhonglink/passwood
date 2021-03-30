package com.ldap.simple;

import com.ldap.util.LdapProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Ldap自动配置类
 *
 * @author sxp
 * @since 2019/5/5
 */
@Configuration
public class LdapConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(LdapConfiguration.class);

    @Autowired
    private LdapProperties ldapProperties;

    private LdapTemplate ldapTemplate;

    @Bean
    public LdapTemplate ldapTemplate() {
        if (ldapTemplate == null) {
            LdapContextSource contextSource = new LdapContextSource();
            contextSource.setUrl(ldapProperties.getServer());
            contextSource.setBase(ldapProperties.getRoot_dn());
            contextSource.setUserDn(ldapProperties.getRoot_user());
            contextSource.setPassword(ldapProperties.getRoot_password());
            contextSource.setAnonymousReadOnly(false);
            contextSource.setPooled(true);

            Map<String, Object> config = new HashMap(2);
            config.put("java.naming.ldap.attributes.binary", "objectGUID");// 解决乱码问题

            contextSource.setBaseEnvironmentProperties(config);
            contextSource.afterPropertiesSet();

            ldapTemplate = new LdapTemplate(contextSource);

            logger.info("创建LdapTemplate实例完毕");
        }
        return ldapTemplate;
    }
}
