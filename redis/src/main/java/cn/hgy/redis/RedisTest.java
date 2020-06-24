package cn.hgy.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class RedisTest {

    private static Jedis jedis;

    public static void main(String[] args) throws InterruptedException {
        try {
            jedis = new Jedis("localhost", 6379);
            testZSet();
        } finally {
            jedis.close();
        }
    }

    /**
     * 测试string数据类型
     *
     * @throws InterruptedException
     */
    private static void testString() throws InterruptedException {

        String key = "string";
        // 1. 先删除指定的key
        jedis.del(key);
        // 2. 添加字符串,过期时间5s，存在则不添加
        jedis.set(key, "value", new SetParams().ex(5).nx());
        // 3. 查询符合模式的所有key
        // h?llo will match hello hallo hhllo
        // h*llo will match hllo heeeello
        // h[ae]llo will match hello and hallo, but not hillo
        String pattern = "str*";
        System.out.println("keys, PATTERN : {" + pattern + "}, RETURN : {" + jedis.keys(pattern) + "}");
        // 4. 查询过期时间
        System.out.println("KEY : {" + key + "}, expire time : {" + jedis.ttl(key) + "}s");
        // 5. 移除过期时间
        jedis.persist(key);
        System.out.println("persist KEY , expire time : {" + jedis.ttl(key) + "}s");
        // 6. 随机访问
        String randomKey = jedis.randomKey();
        // 7. 查看类型
        System.out.println("RANDOM KEY : {" + randomKey + "}，TYPE : {" + jedis.type(randomKey) + "}");
    }

    /**
     * 测试list数据类型
     */
    private static void testList() {
        String key = "list";
        // 1. 先删除指定key
        jedis.del(key);
        // 2. 左添加
        jedis.lpush(key, "listValue3", "listValue2", "listValue1");
        // 右添加
        jedis.rpush(key, "listValue4", "listValue5", "listValue6");
        // 3. 取出所有元素
        System.out.println(jedis.lrange(key, 0, -1));
        // 4. 取出最后一个元素
        System.out.println(jedis.lindex(key, -1));
        // 5. 移除部分数据，保留1-3
        jedis.ltrim(key, 1, 3);
        System.out.println(jedis.lrange(key, 0, -1));
    }

    /**
     * 测试hash数据类型
     */
    private static void testHash() {
        String key = "hash";
        // 1. 先删除指定key
        jedis.del(key);
        Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        // 2. 添加数据结构
        jedis.hset(key, map);
        // 3. 追加数据
        jedis.hset(key, "k3", "v3");
        // 4. 获取数据和长度
        System.out.println("获取所有数据：" + jedis.hgetAll(key) + "，长度：" + jedis.hlen(key));
        // 5. 批量获取
        System.out.println("批量获取数据：" + jedis.hmget(key, "k1", "k3"));
        // 6. 计数
        jedis.hset(key, "k4", 1 + "");
        jedis.hincrBy(key, "k4", 1);
        System.out.println("获取数据：" + jedis.hget(key, "k4"));
    }

    private static void testSet() {
        String key = "set";
        // 1. 先删除指定key
        jedis.del(key);
        // 2. 添加数据结构
        jedis.sadd(key, "value1", "value2");
        // 3. 获取数据和长度
        System.out.println("获取所有数据：" + jedis.smembers(key) + "，长度：" + jedis.scard(key));
        // 4. 是否存在指定value
        System.out.println("是否存在数据：" + jedis.sismember(key, "value2"));
        // 5. 弹出数据
        System.out.println("弹出数据：" + jedis.spop(key));
        // 6.  获取数据
        System.out.println("获取数据：" + jedis.smembers(key));
    }

    private static void testZSet() {
        String key = "zset";
        // 1. 先删除指定key
        jedis.del(key);
        // 2. 添加数据结构
        jedis.zadd(key, 100, "value1");
        jedis.zadd(key, 50, "value2");
        jedis.zadd(key, 0, "value3");
        // 3. 获取数据
        System.out.println("获取数据：" + jedis.zrange(key, 0, 100));
        // 4. 是否存在指定value
        System.out.println("获取数据：" + jedis.zrevrange(key, 0, 100));
        // 5. 数据量
        System.out.println("数据量：" + jedis.zcard(key));
        // 6.  获取数据
        System.out.println("获取数据：" + jedis.zscore(key, "value2"));
        // 7.  获取数据
        System.out.println("删除数据：" + jedis.zrem(key, "value2"));
        System.out.println("获取数据：" + jedis.zrange(key, 0, 100));

    }
}
