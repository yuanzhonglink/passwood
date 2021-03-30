package com.ldap.simple;

import lombok.Data;

/**
 * Ldap中的用户信息对应的实体类
 *
 * @author sxp
 * @since 2019/5/6
 */
@Data
public class LdapUser {
    /**
     * 对应com.orientsec.grpc.admin.entity.User#username
     */
    private String uid;

    /**
     * 对应com.orientsec.grpc.admin.entity.User#email
     */
    private String mail;

    /**
     * 对应com.orientsec.grpc.admin.entity.User#mobile
     */
    private String mobile;

    /**
     * 对应com.orientsec.grpc.admin.entity.User#employeeNumber
     */
    private String employeeNumber;

    /**
     * 对应com.orientsec.grpc.admin.entity.User#nickName (中文姓名)
     */
    private String cn;

}
