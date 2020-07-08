package cn.hgy.week5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Demo {

    private static final int DATA_COUNT = 1000000;
    private static final int AVG = 100000;
    private static final String PRE_KEY = "pre";

    public static void main(String[] args) {

        ConsistentHash cluster = new ConsistentHash();
        cluster.addNode(new Node("192.168.0.1"));
        cluster.addNode(new Node("192.168.0.2"));
        cluster.addNode(new Node("192.168.0.3"));
        cluster.addNode(new Node("192.168.0.4"));
        cluster.addNode(new Node("192.168.0.5"));
        cluster.addNode(new Node("192.168.0.6"));
        cluster.addNode(new Node("192.168.0.7"));
        cluster.addNode(new Node("192.168.0.8"));
        cluster.addNode(new Node("192.168.0.9"));
        cluster.addNode(new Node("192.168.0.10"));

        IntStream.range(0, DATA_COUNT)
                .forEach(index -> {
                    Node node = cluster.get(PRE_KEY + index);
                    node.put(PRE_KEY + index, "Data");
                });

        System.out.println("数据分布情况：");
        List<Double> list = new ArrayList<>(10);
        cluster.getNodes().forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量:" + node.getData().size());
            list.add(Math.pow(node.getData().size() - AVG, 2));
        });

        double variance = list.stream().mapToDouble(Double::doubleValue).sum() / 10;

        System.out.println("标准差：" + Math.sqrt(variance));

        //缓存命中率
        long hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.get(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("缓存命中率：" + hitCount * 1f / DATA_COUNT);

        cluster.addNode(new Node("192.168.0.11"));
        hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.get(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("增加一台机器，缓存命中率：" + hitCount * 1f / DATA_COUNT);
    }

}
