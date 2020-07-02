package com.pass.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.Watcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用Apache Curator对Zookeeper进行监听的工具类
 *
 * @author yuanzhonglin
 * @since 2017/10/26
 */
public class CuratorMonitorUtils {
	/**
	 * 查看并且监听子节点
	 *
	 * @param watcher 需要自定义一个实现Watcher接口的监听类(可以参考单元测试用例)
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static List<String> getAndWatchedChildren(CuratorFramework client,
													 String path,
													 Watcher watcher) throws Exception {
		if (!CuratorUtils.isNodeExists(client, path)) {
			return new ArrayList<>();
		}

		List<String> children = client.getChildren().usingWatcher(watcher).forPath(path);
		return children;
	}

	/**
	 * 监听子节点
	 * <p>
	 * Path Cache：监视一个路径下孩子节点的创建、更新、删除事件 (子节点的子节点不监听) <br>
	 * 备注：测试过程中发现，一旦path对应的节点被删除，原有的监听器会失效  <br>
	 * </p>
	 *
	 * @param listener 实现PathChildrenCacheListener接口的监控类(可以参考单元测试用例)
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static PathChildrenCache pathChildrenListener(CuratorFramework client,
														 String path,
														 PathChildrenCacheListener listener) throws Exception {
		PathChildrenCache cache = new PathChildrenCache(client, path, true);

		cache.getListenable().addListener(listener);
		cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

		return cache;
	}


	/**
	 * 监听当前节点
	 * <p>
	 * Node Cache：监视一个节点的创建、更新、删除，并将节点的数据缓存在本地。
	 * </p>
	 *
	 * @param listener 实现ExtendedNodeCacheListener接口的监控类(可以参考单元测试用例)
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static NodeCache nodeListener(CuratorFramework client,
										 String path,
										 ExtendedNodeCacheListener listener) throws Exception {
		NodeCache cache = new NodeCache(client, path, false);
		listener.setCache(cache);

		cache.getListenable().addListener(listener);
		cache.start();

		return cache;
	}

	/**
	 * 监听当前节点和子节点
	 * <p>
	 * Tree Cache：Path Cache和Node Cache的组合，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子节点的数据。
	 * </p>
	 *
	 * @param listener 实现TreeCacheListener接口的监控类(可以参考单元测试用例)
	 * @author yuanzhonglin
	 * @since 2017/10/26
	 */
	public static TreeCache treeListenter(CuratorFramework client,
										  String path,
										  TreeCacheListener listener) throws Exception {
		TreeCache cache = new TreeCache(client, path);
		cache.getListenable().addListener(listener);
		cache.start();

		return cache;
	}



}
