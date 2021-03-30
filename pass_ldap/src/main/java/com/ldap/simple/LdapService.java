package com.ldap.simple;

/**
 * Ldap服务接口
 *
 * @author sxp
 * @since 2019/5/6
 */
public interface LdapService {
    /**
     * 根据uid查询用户
     *
     * @param uid 用户名
     * @author sxp
     * @since 2019/5/6
     */
    LdapUser findUserByUid(String uid);

    /**
     * 根据employeeNumber查询用户
     *
     * @param employeeNumber 工号
     * @author sxp
     * @since 2019/5/6
     */
    LdapUser findUserByEmployeeNumber(String employeeNumber);

}
