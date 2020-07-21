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
    private List<Long> responseTimeList = new CopyOnWriteArrayList<>();
    private Long avgResponseTime;
    private Long _95ResponseTime;

    public void addResponse(Long responseTime) {
        responseTimeList.add(responseTime);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Long> getResponseTimeList() {
        return responseTimeList;
    }

    public void setResponseTimeList(List<Long> responseTimeList) {
        this.responseTimeList = responseTimeList;
    }

    public Long getAvgResponseTime() {
        long sum = 0;
        int count = 0;
        for (Long l : responseTimeList) {
            sum += (l < 0 ? 0 : l.intValue());
            count += (l < 0 ? 0 : 1);
        }
        return sum / count;
    }

    public void setAvgResponseTime(Long avgResponseTime) {
        this.avgResponseTime = avgResponseTime;
    }

    public Long get_95ResponseTime() {
        int index = Double.valueOf(responseTimeList.size() * 0.95).intValue();
        return responseTimeList.get(index);
    }

    public void set_95ResponseTime(Long _95ResponseTime) {
        this._95ResponseTime = _95ResponseTime;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "url='" + url + '\'' +
                ", avgResponseTime=" + getAvgResponseTime() +
                ", _95ResponseTime=" + get_95ResponseTime() +
                '}';
    }
}
