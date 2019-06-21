package com.pass.curator;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * 对NodeCacheListener进行扩展的接口
 *
 * @author Shawpin Shi
 * @since 2017/10/26
 */
public interface ExtendedNodeCacheListener extends NodeCacheListener {
	void setCache(NodeCache cache);
}
