package cn.hgy.redis;

import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Bit {

    private static Jedis jedis;

    public static void main(String[] args) {
        try {
            jedis = new Jedis("localhost", 6379);

            // 实现用户的签到记录
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
            long beginTime = simpleDateFormat.parse("2020-01-01").getTime();
            // 位移，当天是当年的第几天
            Long day = (System.currentTimeMillis() - beginTime) / 1000 * 60 * 60 * 24L;
            jedis.setbit("user1", day.intValue(), true);
            System.out.println(jedis.getbit("user1", day.intValue()));
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
