package cn.hgy.week5;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 节点，包含IP信息和数据列表。节点可以增加数据，删除数据，获取指定数据
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Node {

    private String ip;

    private Map<String, Object> data = new TreeMap<>();

    public Node(String ip) {
        this.ip = ip;
    }

    /**
     * 添加数据
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void put(String key, T value) {
        data.put(key, value);
    }

    /**
     * 移除数据
     *
     * @param key
     */
    public void remove(String key) {
        data.remove(key);
    }

    /**
     * 获取数据
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
