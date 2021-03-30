package com.ldap.simple;

import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * 将Attributes映射到LdapUser实体类
 *
 * @author sxp
 * @since 2019/5/6
 */
public class LdapUserAttributesMapper implements AttributesMapper<LdapUser> {

    /**
     * 将Attributes映射到LdapUser实体类
     *
     * @param attributes attributes from a SearchResult.
     * @return an object built from the attributes.
     * @throws NamingException if any error occurs mapping the attributes
     */
    @Override
    public LdapUser mapFromAttributes(Attributes attributes) throws NamingException {
        if (attributes == null) {
            return null;
        }

        LdapUser user = new LdapUser();

        if (attributes.get("uid") != null) {
            user.setUid(String.valueOf(attributes.get("uid").get()));
        }

        if (attributes.get("mail") != null) {
            user.setMail(String.valueOf(attributes.get("mail").get()));
        }

        if (attributes.get("mobile") != null) {
            user.setMobile(String.valueOf(attributes.get("mobile").get()));
        }

        if (attributes.get("employeeNumber") != null) {
            user.setEmployeeNumber(String.valueOf(attributes.get("employeeNumber").get()));
        }

        if (attributes.get("cn") != null) {
            user.setCn(String.valueOf(attributes.get("cn").get()));
        }

        return user;
    }
}
