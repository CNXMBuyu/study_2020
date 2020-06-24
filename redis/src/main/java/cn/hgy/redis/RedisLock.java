package cn.hgy.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class RedisLock {

    private static RedissonClient redissonClient;

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        redissonClient = Redisson.create(config);

        new Thread(() -> {
            testFairLock();
        }).start();

        new Thread(() -> {
            testFairLock();
        }).start();
    }

    private static void testLock() {
        try {
            System.out.println(Thread.currentThread().getName());
            RLock rLock = redissonClient.getLock("key");
            // 这一步线程阻塞
            System.out.println(Thread.currentThread().getName() + "，准备加锁。。。。。。");
            rLock.lock(3, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + "加锁成功");
            Thread.sleep(5000);
            rLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testFairLock() {
        try {
            System.out.println(Thread.currentThread().getName());
            RLock rLock = redissonClient.getFairLock("key");
            // 这一步线程阻塞
            System.out.println(Thread.currentThread().getName() + "，准备加锁。。。。。。");
            rLock.lock(30, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + "加锁成功");
            Thread.sleep(30000);
            rLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
