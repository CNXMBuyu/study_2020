package cn.hgy.week7;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 测试结果类
 *
 * @author guoyu.huang
 * @since 2020-07-22
 */
public class TestResult {

    private String url;
    private List<Integer> responseTimeList = new CopyOnWriteArrayList<>();
    private Integer avgResponseTime;
    private Integer _95ResponseTime;

    public void addResponse(Integer responseTime) {
        responseTimeList.add(responseTime);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getResponseTimeList() {
        return responseTimeList;
    }

    public void setResponseTimeList(List<Integer> responseTimeList) {
        this.responseTimeList = responseTimeList;
    }

    public Integer getAvgResponseTime() {
        int sum = 0;
        int count = 0;
        for (Integer l : responseTimeList) {
            sum += (l < 0 ? 0 : l.intValue());
            count += (l < 0 ? 0 : 1);
        }
        return sum / count;
    }

    public void setAvgResponseTime(Integer avgResponseTime) {
        this.avgResponseTime = avgResponseTime;
    }

    public Integer get_95ResponseTime() {
        int index = Double.valueOf(responseTimeList.size() * 0.95).intValue();
        return responseTimeList.get(index);
    }

    public void set_95ResponseTime(Integer _95ResponseTime) {
        this._95ResponseTime = _95ResponseTime;
    }

    @Override
    public String toString() {
        return "测试地址：url=" + url + ", 总次数=" + responseTimeList.size() + ", 失败次数=" +
                responseTimeList.stream().filter(l -> {
                    return l == -1;
                }).count() + ", 平均响应时间=" + getAvgResponseTime() +
                ", 95%响应时间=" + get_95ResponseTime();
    }
}
