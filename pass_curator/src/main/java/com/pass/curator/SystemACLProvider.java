package com.pass.curator;

import org.apache.curator.framework.api.ACLProvider;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认的zookeeper访问控制列表提供者
 *
 * @author Shawpin Shi
 * @since 2017/10/25
 */
public final class SystemACLProvider implements ACLProvider {
	private static final String SCHEME = "digest";
	private static final String USER_PASSWORD = initUserPassword();

	/**
	 * 加载配置文件中的用户名和密码
	 */
	private static String initUserPassword() {
//		Properties properties = SystemConfig.getProperties();
//		String user = PropertiesUtils.getStringValue(properties, ACL_USERNAME, null);
//		String password = PropertiesUtils.getStringValue(properties, ACL_PASSWORD, null);
//
//		if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(password)) {
//			return user + ":" + password;
//		}
		return "user:password";

//		return null;
	}

	private List<ACL> acl;

	@Override
	public List<ACL> getDefaultAcl() {
		if (acl == null) {
			ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL;
			acl.clear();

			String auth;
			try {
				auth = DigestAuthenticationProvider.generateDigest(USER_PASSWORD);
			} catch (NoSuchAlgorithmException e) {
				auth = "";
			}

			acl.add(new ACL(ZooDefs.Perms.ALL, new Id(SCHEME, auth)));
			this.acl = acl;
		}
		return acl;
	}

	@Override
	public List<ACL> getAclForPath(String path) {
		return acl;
	}

	public static String getScheme() {
		return SCHEME;
	}

	public static String getUserPassword() {
		return USER_PASSWORD;
	}
}
