package com.ldap.simple;

import com.alibaba.fastjson.JSON;
import com.ldap.util.LdapProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Ldap服务实现类
 *
 * @author sxp
 * @since 2019/5/6
 */
@Service
public class LdapServiceImpl implements LdapService {
    private static final Logger logger = LoggerFactory.getLogger(LdapServiceImpl.class);

    private static final String[] USER_ATTRIBUTES = {"uid", "cn", "mail", "mobile", "employeeNumber"};

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private LdapProperties ldapProperties;


    /**
     * 唯一的标识名dn的后缀
     * <p>
     * dn示例: uid={0},o=D1108,o=znbm,o=GS001
     * 获取uid={0},之后的内容
     * </p>
     */
    private volatile List<String> patternSuffixes = null;

    private synchronized void initPatternSuffixes() {
        if (patternSuffixes != null) {
            return;
        }

        String[] patternArray = ldapProperties.getPattern();

        if (patternArray == null || patternArray.length == 0) {
            patternSuffixes = new ArrayList<>(0);
            return;
        }

        patternSuffixes = new ArrayList<>(patternArray.length);
        int index;

        for (String item : patternArray) {
            if (StringUtils.isEmpty(item)) {
                continue;
            }
            item = item.trim();
            if (StringUtils.isEmpty(item)) {
                continue;
            }

            if (item.contains("uid={0},")) {
                item = item.replace("uid={0},", "");
            } else {
                index = item.indexOf(',');
                if (index < 0 || index == item.length() - 1) {
                    continue;
                }
                item = item.substring(index + 1);
            }

            item = item.trim();
            if (StringUtils.isEmpty(item)) {
                continue;
            }

            patternSuffixes.add(item);
        }

        logger.info("LDAP pattern suffix list :" + JSON.toJSONString(patternSuffixes));
    }

    @Override
    public LdapUser findUserByUid(String uid) {
        AttributesMapper<LdapUser> mapper = new LdapUserAttributesMapper();
        LdapUser user = null;

        if (patternSuffixes == null) {
            initPatternSuffixes();
        }

        String dn;

        for (String suffix : patternSuffixes) {
            dn = "uid=" + uid + "," + suffix;

            try {
                user = ldapTemplate.lookup(dn, mapper);
            } catch (Exception e) {
                logger.warn("根据[" + dn + "]获取ldap用户信息出错", e);
            }

            if (user != null) {
                break;
            }
        }

        return user;
    }

    /**
     * 根据employeeNumber查询用户
     *
     * @param employeeNumber 工号
     * @author sxp
     * @since 2019/5/6
     */
    @Override
    public LdapUser findUserByEmployeeNumber(String employeeNumber) {
        AttributesMapper<LdapUser> mapper = new LdapUserAttributesMapper();

        List<LdapUser> users = new ArrayList<>(1);

        if (patternSuffixes == null) {
            initPatternSuffixes();
        }

        List<LdapUser> searchUsers = null;
        LdapQuery query;

        for (String suffix : patternSuffixes) {
            try {
                query = LdapQueryBuilder.query()
                        .base(suffix)
                        .attributes(USER_ATTRIBUTES)
                        .where("employeeNumber").is(employeeNumber);
                searchUsers = ldapTemplate.search(query, mapper);
            } catch (Exception e) {
                logger.warn("根据[" + suffix + "]和[employeeNumber=" + employeeNumber + "]获取ldap用户信息出错", e);
            }

            if (searchUsers != null && !searchUsers.isEmpty()) {
                users.addAll(searchUsers);
                break;
            }
        }

        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }


}
