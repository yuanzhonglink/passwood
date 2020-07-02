package com.pass.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.curator.utils.PathUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用Apache Curator对Zookeeper进行常用的操作工具类
 *
 * @author yuanzhonglin
 * @since 2017/10/25
 */
public class CuratorUtils {
	private static int baseSleepTimeMs = 1000;
	private static int maxRetries = 3;
	private static int connectionTimeoutMs = 15 * 1000;



	/**
	 * 创建客户端
	 *
	 * @param connectString 连接zookeeper的字符串，支持集群的方式，多个地址之间使用英文逗号分隔
	 * @author yuanzhonglin
	 * @since 2017/10/25
	 */
	public static CuratorFramework createClient(String connectString) {
		// zk断线重连时，每3秒重连一次，直到连接上zk，或者超过最大重连时间才停止
		int maxElapsedTimeMs = 86400000;// 缺省值为1天(86400*1000)
		int sleepMsBetweenRetries = 3 * 1000;
		RetryUntilElapsed retryPolicy = new RetryUntilElapsed(maxElapsedTimeMs, sleepMsBetweenRetries);

		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString(connectString)
				.retryPolicy(retryPolicy)
				.connectionTimeoutMs(connectionTimeoutMs)
				.build();

		return client;
	}

	/**
	 * 创建带访问控制列表的客户端
	 *
	 * @param connectString 连接zookeeper的字符串，支持集群的方式，多个地址之间使用英文逗号分隔
	 * @author yuanzhonglin
	 * @since 2017/10/25
	 */
	public static CuratorFramework createClientWithACL(String connectString) {
		ACLProvider aclProvider = new SystemACLProvider();
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);

		String scheme = SystemACLProvider.getScheme();
		byte[] auth = SystemACLProvider.getUserPassword().getBytes(StandardCharsets.UTF_8);

		CuratorFramework client = CuratorFrameworkFactory.builder().aclProvider(aclProvider)
				.authorization(scheme, auth)
				.connectionTimeoutMs(connectionTimeoutMs)
				.connectString(connectString)
				.retryPolicy(retryPolicy)
				.build();

		return client;
	}


	/**
	 * 判断节点是否已经存在
	 *
	 * @author yuanzhonglin
	 * @since 2017/10/25
	 */
	public static boolean isNodeExists(CuratorFramework client, String path) throws Exception {
		path = PathUtils.validatePath(path);

		Stat stat = client.checkExists().forPath(path);
		return (stat != null) ? true : false;
	}

	/**
	 * 创建永久节点
	 *
	 * @author yuanzhonglin
	 * @since 2017/10/25
	 */
	public static boolean createNode(CuratorFramework client, String path, byte[] data) throws Exception {
		if (isNodeExists(client, path)) {
			return false;
		} else {
			client.create().creatingParentContainersIfNeeded().forPath(path, data);
			return true;
		}
	}

	/**
	 * 创建临时节点
	 *
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static boolean createEphemeralNode(CuratorFramework client, String path, byte[] data) throws Exception {
		if (isNodeExists(client, path)) {
			return false;
		} else {
			client.create().creatingParentContainersIfNeeded()
					.withMode(CreateMode.EPHEMERAL)
					.forPath(path, data);
			return true;
		}
	}

	/**
	 * 读取节点的数据
	 *
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static byte[] retrieveData(CuratorFramework client, String path) throws Exception {
		if (!isNodeExists(client, path)) {
			return new byte[0];
		}

		byte[] data = client.getData().forPath(path);
		return data;
	}

	/**
	 * 修改节点的数据
	 *
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static boolean updateData(CuratorFramework client, String path, byte[] newData) throws Exception {
		if (!isNodeExists(client, path)) {
			return false;
		} else {
			client.setData().forPath(path, newData);
			return true;
		}
	}

	/**
	 * 删除节点
	 *
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static boolean deleteNode(CuratorFramework client, String path) throws Exception {
		if (!isNodeExists(client, path)) {
			return false;
		} else {
			client.delete().forPath(path);
			return true;
		}
	}

	/**
	 * 获取某个节点的下面的子节点
	 * <p>
	 * 不会递归获取
	 * </p>
	 *
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static List<String> getChildren(CuratorFramework client, String path) throws Exception {
		if (!isNodeExists(client, path)) {
			return new ArrayList<>();
		}

		List<String> children = client.getChildren().watched().forPath(path);
		return children;
	}



}
