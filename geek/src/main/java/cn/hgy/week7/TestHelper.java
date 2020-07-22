package cn.hgy.week7;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试助手
 *
 * @author guoyu.huang
 * @since 2020-07-22
 */
public class TestHelper {

    /**
     * 测试
     *
     * @param url
     * @param totalRequestCount
     * @param concurrencyCount
     * @return
     */
    public static TestResult test(String url, int totalRequestCount, int concurrencyCount) {

        // 等待所有线程都执行完之后，才返回
        CountDownLatch countDownLatch = new CountDownLatch(totalRequestCount);
        // 分配线程，核心线程数为最大并发数，等待队列为请求总数
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(concurrencyCount, concurrencyCount, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(totalRequestCount));

        // 测试结果
        TestResult result = new TestResult();
        result.setUrl(url);

        // 添加都线程队列
        int count = totalRequestCount;

        System.out.println("开始执行测测试用例。。。。。。");
        long allBegin = System.currentTimeMillis();

        while (count > 0) {

            threadPoolExecutor.submit(() -> {
                try {
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpGet httpGet = new HttpGet(url);
                    long begin = System.currentTimeMillis();
                    HttpResponse response = client.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        result.addResponse(Long.valueOf(System.currentTimeMillis() - begin).intValue());
                    } else {
                        result.addResponse(-1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
            count--;
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("执行测测试用例结束。。。。。。耗时：" + (System.currentTimeMillis() - allBegin));
        return result;
    }
}
