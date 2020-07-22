package cn.hgy.week7;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
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

        CountDownLatch countDownLatch = new CountDownLatch(totalRequestCount);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(concurrencyCount, concurrencyCount, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(totalRequestCount));

        TestResult result = new TestResult();
        result.setUrl(url);

        int count = totalRequestCount;

        while (count > 0) {
            threadPoolExecutor.submit(() -> {
                long begin = System.currentTimeMillis();
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet get = new HttpGet(url);
                HttpResponse response = null;
                try {
                    response = client.execute(get);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result.addResponse(Long.valueOf(System.currentTimeMillis() - begin).intValue());
                } else {
                    result.addResponse(-1);
                }
                countDownLatch.countDown();
            });
            count--;
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
