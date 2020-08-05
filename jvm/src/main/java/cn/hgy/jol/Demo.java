package cn.hgy.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author guoyu.huang
 * @since 2020-07-24
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {

        // 开启及时偏向锁
        // VM：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
        // 自旋次数
        // VM：-XX:PreBlockSpin=10

//        testBiasedLocking();
//        testRebias();
//        testLightweight();
        testHeavyweight();

    }

    /**
     * 验证偏向锁
     * <pre>
     *      默认偏向锁有延迟，为了测试方便，直接设置0延迟
     *      VM参数：-XX:BiasedLockingStartupDelay=0
     * </pre>
     *
     * @throws InterruptedException
     */
    public static void testBiasedLocking() throws InterruptedException {
        Obj obj = new Obj();

        System.out.println("初始内容----------------------");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        synchronized (obj) {
            System.out.println("偏向内容----------------------");
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
    }

    /**
     * 测试重偏向
     * <pre>
     *      默认偏向锁有延迟，为了测试方便，直接设置0延迟： -XX:BiasedLockingStartupDelay=0
     *      跟踪偏向锁：-XX:+TraceBiasedLocking
     * </pre>
     *
     * @throws InterruptedException
     */
    public static void testRebias() throws InterruptedException {

        List<Obj> objList = new ArrayList<>();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Obj obj = new Obj();
                synchronized (obj) {
                    objList.add(obj);
                }
            }
            try {
                //为了防止JVM线程复用，在创建完对象后，保持线程存活状态
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(3000);
        System.out.println("objList中第20个对象的偏向内容----------------------");
        System.out.println((ClassLayout.parseInstance(objList.get(19)).toPrintable()));

        //创建另一个线程去竞争之前线程中已经退出同步块的锁
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                Obj obj = objList.get(i);
                synchronized (obj) {
                    //分别打印第19次和第20次偏向锁重偏向结果
                    if (i == 18 || i == 19) {

                        System.out.println("第" + (i + 1) + "次偏向结果");
                        System.out.println((ClassLayout.parseInstance(obj).toPrintable()));
                    }
                }
            }
            try {
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * 轻量锁
     * <pre>
     *      默认偏向锁有延迟，为了测试方便，直接设置0延迟
     *      VM参数：-XX:BiasedLockingStartupDelay=0
     * </pre>
     *
     * @throws InterruptedException
     */
    public static void testLightweight() throws InterruptedException {

        Obj obj = new Obj();

        new Thread(() -> {
            synchronized (obj) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(200);

        System.out.println("----------------------");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(() -> {
            synchronized (obj) {
                System.out.println("T2----------------------");
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }

        }).start();

        Thread.sleep(200);

        System.out.println("----------------------");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

    }

    /**
     * 重量锁
     * <pre>
     *      默认偏向锁有延迟，为了测试方便，直接设置0延迟
     *      VM参数：-XX:BiasedLockingStartupDelay=0
     * </pre>
     *
     * @throws InterruptedException
     */
    public static void testHeavyweight() throws InterruptedException {
        Obj obj = new Obj();

        new Thread(() -> {
            synchronized (obj) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(200);

        System.out.println("----------------------");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(() -> {
            synchronized (obj) {
                System.out.println("T2----------------------");
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }

        }).start();

        Thread.sleep(5000);

        System.out.println("----------------------");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }

}
