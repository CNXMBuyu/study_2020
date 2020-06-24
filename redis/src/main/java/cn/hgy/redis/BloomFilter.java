package cn.hgy.redis;

import io.rebloom.client.Client;
import io.rebloom.client.Command;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class BloomFilter {

    private static Jedis jedis;

    public static void main(String[] args) {
        try {
            jedis = new Jedis("localhost", 6379);

            // 设置布隆过滤器参数
            jedis.sendCommand(io.rebloom.client.Command.RESERVE, "bf", "0.01", "5");

            // 第一种场景：数据去重
            // 当使用过数据，添加到redis，通过exists来判断是否存在：0为不存在，1为存在
            jedis.sendCommand(io.rebloom.client.Command.ADD, "bf", "user1");
            jedis.sendCommand(io.rebloom.client.Command.ADD, "bf", "user2");

            System.out.println(jedis.sendCommand(io.rebloom.client.Command.EXISTS, "bf", "user1"));
            System.out.println(jedis.sendCommand(io.rebloom.client.Command.EXISTS, "bf", "user3"));

            // 第二种场景：缓存穿透
            // 将所有缓存预热到指定key中，存在则查询，不存在则直接返回
            Set<String> keys = jedis.keys("*");
            keys.forEach(key->{
                jedis.sendCommand(Command.ADD, "all", key);
            });

            long result = (Long) jedis.sendCommand(io.rebloom.client.Command.EXISTS, "all", "user3");
            if(result == 1){
                System.out.println("执行缓存查询");
            }else{
                System.out.println("直接返回");
            }


        } finally {
            jedis.close();
        }
    }
}
