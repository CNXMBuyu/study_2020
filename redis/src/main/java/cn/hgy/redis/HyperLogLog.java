package cn.hgy.redis;

import redis.clients.jedis.Jedis;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class HyperLogLog {

    private static Jedis jedis;

    public static void main(String[] args) throws InterruptedException {
        // 大致统计用户数量
        try {
            jedis = new Jedis("localhost", 6379);
            for (int i = 0; i < 1000; i++) {
                jedis.pfadd("hyper1", "user" + i);
            }
            System.out.println(jedis.pfcount("hyper1"));

            for (int i = 0; i < 1000; i++) {
                jedis.pfadd("hyper2", "user" + (1000 + i));
            }
            System.out.println(jedis.pfcount("hyper2"));

            jedis.pfmerge("hyper3", "hyper1", "hyper2");
            System.out.println(jedis.pfcount("hyper3"));
        } finally {
            jedis.close();
        }
    }
}
