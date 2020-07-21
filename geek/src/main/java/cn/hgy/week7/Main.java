package cn.hgy.week7;

/**
 * @author guoyu.huang
 * @since 2020-07-22
 */
public class Main {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        TestResult testResult = TestHelper.test("http://www.baidu.com", 100,5);
        System.out.println(testResult.toString());

        testResult.getResponseTimeList().forEach(l->{
            System.out.print(l + ",");
        });
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("总耗时：" + (end - begin));
    }
}
