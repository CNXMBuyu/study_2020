package cn.hgy.redis;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.GeoRadiusParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class GeoHash {

    private static Jedis jedis;

    public static void main(String[] args) {
        try {
            Jedis jedis = new Jedis("localhost", 6379);

            String key = "company";
            jedis.geoadd(key, 116.48105, 39.996794, "掘金");

            Map<String, GeoCoordinate> map = new HashMap<>(3);
            map.put("掌阅", new GeoCoordinate(116.514203, 39.905409));
            map.put("美团", new GeoCoordinate(116.489033, 40.007669));
            map.put("京东", new GeoCoordinate(116.562108, 39.9787602));

            jedis.geoadd(key, map);
            System.out.println("美团、京东距离："+ jedis.geodist(key, "美团","京东", GeoUnit.M) + "米");

            // 范围20公里内的三家公司
           List<GeoRadiusResponse> list = jedis.georadiusByMember(key, "京东", 20, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().count(3));
           list.forEach(geoRadiusResponse -> {
               System.out.println(geoRadiusResponse.getMemberByString() + geoRadiusResponse.getDistance());
           });

            List<GeoRadiusResponse> list2 = jedis.georadius(key, 116.562108, 39.9787602, 20, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().count(3));
            list2.forEach(geoRadiusResponse -> {
                System.out.println(geoRadiusResponse.getMemberByString() + geoRadiusResponse.getDistance());
            });
        } finally {
            jedis.close();
        }
    }
}
