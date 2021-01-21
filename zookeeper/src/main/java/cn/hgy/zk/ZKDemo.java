package cn.hgy.zk;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class ZKDemo {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        });

        zooKeeper.delete("/hgy", 0);
        String path1 = zooKeeper.create("/hgy", "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("持久节点：" + path1);

        zooKeeper.delete("/hgy-0000000069", 0);
        String path2 = zooKeeper.create("/hgy-", "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("持久顺序节点：" + path2);

//        String path3 = zooKeeper.create("/hgy-ttl-", "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_WITH_TTL);
//        System.out.println("持久节点:"+ path3);

        String path4 = zooKeeper.create("/tmp-hgy", "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("临时节点：" + path4);

        String path5 = zooKeeper.create("/tmp-hgy-", "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("临时顺序节点：" + path5);

    }
}
