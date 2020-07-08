package cn.hgy.week5;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class ConsistentHash {

    /**
     * 真实节点
     */
    private List<Node> nodes = new ArrayList<>();

    /**
     * 虚拟节点
     */
    private SortedMap<Long, Node> virtualNodes = new TreeMap();

    /**
     * 虚拟节点个数
     */
    private static final int VIRTUAL_NODE_COUNT = 5000;

    /**
     * 添加节点
     *
     * @param node
     */
    public void addNode(Node node) {
        this.nodes.add(node);
        // 分配虚拟节点
        IntStream.range(0, VIRTUAL_NODE_COUNT).forEach(index -> {
            long hash = hash(node.getIp() + index);
            virtualNodes.put(hash, node);
        });
    }

    /**
     * 删除节点
     *
     * @param node
     */
    public void removeNode(Node node) {
        this.nodes.removeIf(o -> o.getIp().equals(node.getIp()));
        // 删除虚拟节点
        IntStream.range(0, VIRTUAL_NODE_COUNT).forEach(index -> {
            long hash = hash(node.getIp() + index);
            virtualNodes.remove(hash);
        });
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    public Node get(String key) {
        long hash = hash(key);
        SortedMap<Long, Node> subMap = (hash >= virtualNodes.lastKey() ? virtualNodes.tailMap(0L) : virtualNodes.tailMap(hash));
        if (subMap.isEmpty()) {
            return null;
        }
        return subMap.get(subMap.firstKey());
    }

    /**
     * 用FNV1_32_HASH算法计算服务器的Hash值
     *
     * @param key
     * @return
     */
    private long hash(String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }

        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public SortedMap<Long, Node> getVirtualNodes() {
        return virtualNodes;
    }
}
