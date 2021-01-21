package cn.hgy.juc;

import java.util.concurrent.*;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class ThreadPoolDemo {


    public static void main(String[] args) {
        testThreadPool();
//        testFixedThreadPool();
//        testCachedThreadPool();

    }

    private static void testThreadPool() {
        // 核心线程3，最大线程5，当非核心线程空闲超过10秒则释放资源
        // 等待线程数，最多不超过20个，自定义异常处理机制
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5,
                10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(20), (runnable, executor) -> {
            System.out.println("Error: active-" + executor.getActiveCount() + ", max-active-" + executor.getMaximumPoolSize());
        });
        for (int i = 0; i < 50; i++) {
            System.out.println(i);
            threadPoolExecutor.submit(() -> {
                try {
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private static void testFixedThreadPool() {
        // 10个线程，Integer.MAX_VALUE等待队列
        //  new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId());
            });
        }
    }

    private static void testCachedThreadPool() {
        // 0个核心线程，Integer.MAX_VALUE非核心线程
        // SynchronousQueue等待队列
        //  new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId());
            });
        }
    }
}
