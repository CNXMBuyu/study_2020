package cn.hgy.week7;

import java.util.stream.Collectors;

/**
 * @author guoyu.huang
 * @since 2020-07-22
 */
public class Main {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        // 将域名解析成IP
        TestResult testResult = TestHelper.test("http://14.215.177.39", 100, 10);
        System.out.println("测试结果为：" + testResult.toString());
        System.out.println("按顺序执行响应结果如下：");
        testResult.getResponseTimeList().forEach(l -> {
            System.out.print(l + ",");
        });
        System.out.println();
        System.out.println("按响应顺序排序之后结果如下：");
        testResult.getResponseTimeList().stream().sorted().forEach(l -> {
            System.out.print(l + ",");
        });
    }
}
