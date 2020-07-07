package cn.hgy.week5;

import java.util.stream.IntStream;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class Demo {

    private static final int DATA_COUNT = 60000;
    private static final String PRE_KEY = "pre";

    public static void main(String[] args) {
        ConsistentHash cluster = new ConsistentHash();
        cluster.addNode(new Node("192.168.0.1"));
        cluster.addNode(new Node("192.168.0.2"));
        cluster.addNode(new Node("192.168.0.3"));
//        cluster.addNode(new Node("192.168.0.4"));

        IntStream.range(0, DATA_COUNT)
                .forEach(index -> {
                    Node node = cluster.get(PRE_KEY + index);
                    node.put(PRE_KEY + index, "Data");
                });

        System.out.println("数据分布情况：");
        cluster.getNodes().forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量:" + node.getData().size());
        });

        //缓存命中率
        long hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.get(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("缓存命中率：" + hitCount * 1f / DATA_COUNT);

        cluster.addNode(new Node("192.168.0.4"));
        hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.get(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("缓存命中率：" + hitCount * 1f / DATA_COUNT);
    }
}
