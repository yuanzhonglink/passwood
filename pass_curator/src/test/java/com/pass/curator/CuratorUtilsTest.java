package com.pass.curator;

import com.pass.curator.util.IpUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Unit Test for CuratorUtils
 *
 * @author Shawpin Shi
 * @since 2017/10/26
 */
public class CuratorUtilsTest {
	private static String connectionString = "127.0.0.1:2181,127.0.0.2:2181";
	private static CuratorFramework client;
	private static String localIp = IpUtils.getLocalHostAddress();

	@BeforeClass
	public static void setUp() throws Exception {
		client = CuratorUtils.createClient(connectionString);
		//client = CuratorUtils.createClientWithACL(connectionString);
		client.start();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		if (client != null) {
			CloseableUtils.closeQuietly(client);
		}
	}

	@Test
	public void testIsNodeExists() throws Exception {
		String path = "/zookeeper/quota";
		Assert.assertTrue(CuratorUtils.isNodeExists(client, path));
	}

	@Test
	public void testCreateEphemeralNode() throws Exception {
		String path = "/shixiaoping/EphemeralNode";
		byte[] data = localIp.getBytes(StandardCharsets.UTF_8);

		CuratorUtils.createEphemeralNode(client, path, data);
		Assert.assertTrue(CuratorUtils.isNodeExists(client, path));
		TimeUnit.SECONDS.sleep(2L);

		CloseableUtils.closeQuietly(client);

		client = CuratorUtils.createClientWithACL(connectionString);// 必须重新创建
		client.start();
		Assert.assertFalse(CuratorUtils.isNodeExists(client, path));
	}

	@Test
	public void testCrud() throws Exception {
		String path = "/shixiaoping/test1";
		byte[] data = localIp.getBytes(StandardCharsets.UTF_8);

		CuratorUtils.createNode(client, path, data);
		Assert.assertTrue(CuratorUtils.isNodeExists(client, path));

		byte[] dataOfRead = CuratorUtils.retrieveData(client, path);
		Assert.assertTrue(Arrays.equals(data, dataOfRead));

		byte[] newData = (new String(data, StandardCharsets.UTF_8) + "-modify").getBytes(StandardCharsets.UTF_8);
		CuratorUtils.updateData(client, path, newData);

		dataOfRead = CuratorUtils.retrieveData(client, path);
		Assert.assertTrue(Arrays.equals(newData, dataOfRead));

		Assert.assertTrue(CuratorUtils.deleteNode(client, path));
		Assert.assertFalse(CuratorUtils.isNodeExists(client, path));
	}

	@Test
	public void testGetChildrenPath() throws Exception {
		String root = "/shixiaoping";

		byte[] data = localIp.getBytes(StandardCharsets.UTF_8);

		CuratorUtils.createNode(client, root + "/test1", data);
		CuratorUtils.createNode(client, root + "/test2", data);
		CuratorUtils.createNode(client, root + "/test3", data);
		CuratorUtils.createNode(client, root + "/test3/test31", data);

		List<String> children = CuratorUtils.getChildren(client, root);

		Assert.assertEquals(3, children.size());
		Assert.assertTrue(children.contains("test1"));// 注意：子节点不是全路径，并且没有/字符
		Assert.assertTrue(children.contains("test2"));
		Assert.assertTrue(children.contains("test3"));

		CuratorUtils.deleteNode(client, root + "/test1");
		CuratorUtils.deleteNode(client, root + "/test2");
		CuratorUtils.deleteNode(client, root + "/test3/test31");
		CuratorUtils.deleteNode(client, root + "/test3");
	}

}
