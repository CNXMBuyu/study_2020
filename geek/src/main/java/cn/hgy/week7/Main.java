package cn.hgy.week7;

import java.util.stream.Collectors;

/**
 * @author guoyu.huang
 * @since 2020-07-22
 */
public class Main {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        TestResult testResult = TestHelper.test("http://www.baidu.com", 100,10);
        System.out.println(testResult.toString());

        testResult.getResponseTimeList().stream().sorted().collect(Collectors.toList()).forEach(l->{
            System.out.print(l + ",");
        });
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("总耗时：" + (end - begin));
    }
}
