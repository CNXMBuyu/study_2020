package cn.hgy.juc;

import java.util.concurrent.*;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
public class CallbackDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        testJoin();
        testFutureTask();
    }

    private static void testFutureTask() throws ExecutionException, InterruptedException {

        FutureTask<String> task = new FutureTask<>(() -> {
            Thread.sleep(10000);
            return "aaa";
        });
        Thread thread = new Thread(task);
        // 线程并行
        thread.start();
        System.out.println(System.currentTimeMillis() + "-Thread-" + thread.getId() + "-start");
        System.out.println(System.currentTimeMillis() + task.get());

        FutureTask<String> task1 = new FutureTask<>(() -> {
            Thread.sleep(10000);
            return "bbb";
        });
        Thread thread1 = new Thread(task1);
        thread1.run();
        System.out.println(System.currentTimeMillis() + "-Thread-" + thread1.getId() + "-run");
        System.out.println(System.currentTimeMillis() + task1.get());

    }

    private static void testThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(()->{
            System.out.println(Thread.currentThread().getId() + " execute");
        });

        Future future = executorService.submit(()->{
            System.out.println(Thread.currentThread().getId() + " submite");
            return "123";
        });
        System.out.println(future.get());
    }

    private static void testJoin() throws InterruptedException {

        Thread thread3 = new Thread(() -> {
            System.out.println(Thread.currentThread().getId() + "3");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getId() + "2");
        });

        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getId() + "1");
        });

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();

    }
}
