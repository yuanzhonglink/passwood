package com.pass.curator;

import com.google.common.base.Preconditions;
import com.pass.curator.util.IpUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Unit test for CuratorMonitorUtils
 *
 * @author Shawpin Shi
 * @since 2017/10/26
 */
public class CuratorMonitorUtilsTest {
	private static Logger logger = Logger.getLogger(CuratorMonitorUtilsTest.class.getName());

	private static String connectionString = "127.0.0.1:2181";
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
	public void testGetAndWatchedChildren() throws Exception {
		String root = "/Application/grpc";

		byte[] data = localIp.getBytes(StandardCharsets.UTF_8);

		CuratorUtils.createNode(client, root + "/test1", data);
		CuratorUtils.createNode(client, root + "/test2", data);
		CuratorUtils.createNode(client, root + "/test3", data);
		CuratorUtils.createNode(client, root + "/test3/test31", data);

		Watcher watcher = new MyWatcher(client);
		List<String> children = CuratorMonitorUtils.getAndWatchedChildren(client, root, watcher);

		Assert.assertEquals(3, children.size());
		Assert.assertTrue(children.contains("test1"));
		Assert.assertTrue(children.contains("test2"));
		Assert.assertTrue(children.contains("test3"));

		CuratorUtils.createNode(client, root + "/test4", data);
		//TimeUnit.SECONDS.sleep(1L);
		CuratorUtils.createNode(client, root + "/test5", data);
		//TimeUnit.SECONDS.sleep(1L);

		// 开启事务
		CuratorTransaction transaction = client.inTransaction();
		transaction.delete().forPath(root + "/test4")
				.and()
				.delete().forPath(root + "/test5")
				.and()
				.commit();// 提交事务

		//TimeUnit.SECONDS.sleep(1L);

		CuratorUtils.deleteNode(client, root + "/test1");
		CuratorUtils.deleteNode(client, root + "/test2");
		CuratorUtils.deleteNode(client, root + "/test3/test31");
		CuratorUtils.deleteNode(client, root + "/test3");
	}

	// 能接受多个变化事件的Watcher
	private static class MyWatcher implements Watcher {
		private CuratorFramework client;

		public MyWatcher(CuratorFramework client) {
			this.client = client;
		}

		@Override
		public void process(WatchedEvent event) {
			try {
				System.out.println("----process begin----");

				String path = event.getPath();
				if (path == null) {
					logger.warning("Ignore this watched event(" + event + ").");
					return;
				}

				List<String> children = client.getChildren().usingWatcher(this).forPath(event.getPath());
				for (String child : children) {
					System.out.println(child);
				}

				System.out.println("----process end------");
			} catch (Exception e) {
				if (CuratorFrameworkState.STOPPED != client.getState()) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
	}

	@Test
	public void testPathChildrenListener() throws Exception {
		String root = "/yuanzhonglin";

		if (!CuratorUtils.isNodeExists(client, root)) {
			CuratorUtils.createNode(client, root, new byte[0]);
		}

		PathChildrenCacheListener listener = new MyPathChildrenListener();
		PathChildrenCache cache = CuratorMonitorUtils.pathChildrenListener(client, root, listener);

		byte[] data = localIp.getBytes(StandardCharsets.UTF_8);

		CuratorTransaction transaction = client.inTransaction();
		transaction.create().forPath(root + "/test1", data)
				.and()
				.create().forPath(root + "/test2", data)
				.and()
				.create().forPath(root + "/test3", data)
				.and()
				.create().forPath(root + "/test3/test31", data)
				.and()
				.commit();// 提交事务

		CuratorUtils.createNode(client, root + "/test4", data);

        //TimeUnit.SECONDS.sleep(30L);

		transaction = client.inTransaction();
		transaction.delete().forPath(root + "/test1")
				.and()
				.delete().forPath(root + "/test2")
				.and()
				.delete().forPath(root + "/test3/test31")
				.and()
				.delete().forPath(root + "/test3")
				.and()
				.delete().forPath(root + "/test4")
				.and()
				.commit();

        TimeUnit.SECONDS.sleep(3L);
		CloseableUtils.closeQuietly(cache);// 使用完成后关闭监听，否则后台进程会报错
	}

	private static class MyPathChildrenListener implements PathChildrenCacheListener {
		@Override
		public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
			PathChildrenCacheEvent.Type type = event.getType();

			System.out.println("----childEvent begin----");

			ChildData eventData = event.getData();
			System.out.println("PathChildren cache event type: " + type);

			if (eventData != null) {
				String path = eventData.getPath();
				if (path == null) {
					logger.warning("Ignore this watched event(" + event + ").");
					return;
				}

				String data = new String(eventData.getData());

				switch (type) {
					case CHILD_ADDED:
						System.out.println(type.name() + ":" + path + "  数据:" + data);
						break;
					case CHILD_REMOVED:
						System.out.println(type.name() + ":" + path + "  数据:" + data);
						break;
					case CHILD_UPDATED:
						System.out.println(type.name() + ":" + path + "  数据:" + data);
						break;
					default:
						break;
				}
			}

			System.out.println("----childEvent end----");
		}
	}

	@Test
	public void testNodeListener() throws Exception {
		String path = "/yuanzhonglin";

		if (!CuratorUtils.isNodeExists(client, path)) {
			CuratorUtils.createNode(client, path, new byte[0]);
		}

		ExtendedNodeCacheListener listener = new MyNodeCacheListener();
		NodeCache cache = CuratorMonitorUtils.nodeListener(client, path, listener);

		byte[] data = CuratorUtils.retrieveData(client, path);

		byte[] newData = (new String(data, StandardCharsets.UTF_8) + "-modify").getBytes(StandardCharsets.UTF_8);
		CuratorUtils.updateData(client, path, newData);

		CuratorUtils.deleteNode(client, path);// 删除该节点后监听自动退出

		CuratorUtils.createNode(client, path, new byte[0]);

		CloseableUtils.closeQuietly(cache);// 使用完成后关闭监听，否则后台进程会报错
	}

	private static class MyNodeCacheListener implements ExtendedNodeCacheListener {
		private NodeCache cache;

		@Override
		public void setCache(NodeCache cache) {
			this.cache = cache;
		}

		@Override
		public void nodeChanged() throws Exception {
			Preconditions.checkNotNull(cache, "cache");

			ChildData data = cache.getCurrentData();
			if (data != null) {
				System.out.println("the test node is change and result is :");
				System.out.println("path : " + data.getPath());
				System.out.println("data : " + new String(data.getData()));
				System.out.println("stat : " + data.getStat());
			} else {
				System.out.println("the node was deleted.");
			}
		}
	}

	@Test
	public void testTreeListener() throws Exception {
		String root = "/dubbo/config";

		if (!CuratorUtils.isNodeExists(client, root)) {
			CuratorUtils.createNode(client, root, new byte[0]);
		}

		TreeCacheListener listener = new MyTreeCacheListener();
		TreeCache cache = CuratorMonitorUtils.treeListenter(client, root, listener);

		while (true) {

		}

//		byte[] data = localIp.getBytes(StandardCharsets.UTF_8);
//
//		// add
//		CuratorTransaction transaction = client.inTransaction();
//		transaction.create().forPath(root + "/test1", data)
//				.and()
//				.create().forPath(root + "/test2", data)
//				.and()
//				.create().forPath(root + "/test3", data)
//				.and()
//				.create().forPath(root + "/test3/test31", data)
//				.and()
//				.commit();// 提交事务
//
//		CuratorUtils.createNode(client, root + "/test4", data);
//
//		// update
//		byte[] timeData = String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8);
//		CuratorUtils.updateData(client, root, timeData);
//		CuratorUtils.updateData(client, root + "/test3/test31", timeData);
//		TimeUnit.SECONDS.sleep(1L);
//
//		// delete
//		transaction = client.inTransaction();
//		transaction.delete().forPath(root + "/test1")
//				.and()
//				.delete().forPath(root + "/test2")
//				.and()
//				.delete().forPath(root + "/test3/test31")
//				.and()
//				.delete().forPath(root + "/test3")
//				.and()
//				.commit();
//
//		CuratorUtils.deleteNode(client, root + "/test4");

//		CloseableUtils.closeQuietly(cache);// 使用完成后关闭监听，否则后台进程会报错
	}

	private static class MyTreeCacheListener implements TreeCacheListener {
		@Override
		public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
			ChildData eventData = event.getData();

			if (eventData != null) {
				String path = eventData.getPath();
				String data = new String(eventData.getData());
				TreeCacheEvent.Type type = event.getType();

				System.out.println("----nodeEvent begin----");

				switch (type) {
					case NODE_ADDED:
						System.out.println(type + ":" + path + "  数据:" + data);
						break;
					case NODE_REMOVED:
						System.out.println(type + ":" + path + "  数据:" + data);
						break;
					case NODE_UPDATED:
						System.out.println(type + ":" + path + "  数据:" + data);
						break;
					default:
						break;
				}

				System.out.println("----nodeEvent end----");
			} else {
				// do nothing
			}
		}
	}
}
