package cn.hgy;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author guoyu.huang
 * @since 2021-01-15
 */
public class DfsNodeAnalyze {

    public static void main(String[] args) {
        String json = "{\"xmqyrz09:9866\":{\"infoAddr\":\"19.82.92.40:9864\",\"infoSecureAddr\":\"19.82.92.40:0\",\"xferaddr\":\"19.82.92.40:9866\",\"lastContact\":0,\"usedSpace\":129506254848,\"adminState\":\"In Service\",\"nonDfsUsedSpace\":39391121408,\"capacity\":528293822464,\"numBlocks\":962,\"version\":\"3.2.1\",\"used\":129506254848,\"remaining\":332536963072,\"blockScheduled\":0,\"blockPoolUsed\":129506254848,\"blockPoolUsedPercent\":24.51406,\"volfails\":0,\"lastBlockReport\":200},\"xmqyrz08:9866\":{\"infoAddr\":\"19.82.92.39:9864\",\"infoSecureAddr\":\"19.82.92.39:0\",\"xferaddr\":\"19.82.92.39:9866\",\"lastContact\":2,\"usedSpace\":133186510848,\"adminState\":\"In Service\",\"nonDfsUsedSpace\":47658049536,\"capacity\":528293822464,\"numBlocks\":989,\"version\":\"3.2.1\",\"used\":133186510848,\"remaining\":320589778944,\"blockScheduled\":0,\"blockPoolUsed\":133186510848,\"blockPoolUsedPercent\":25.210688,\"volfails\":0,\"lastBlockReport\":138},\"xmqyrz11:9866\":{\"infoAddr\":\"19.82.92.42:9864\",\"infoSecureAddr\":\"19.82.92.42:0\",\"xferaddr\":\"19.82.92.42:9866\",\"lastContact\":2,\"usedSpace\":133592313856,\"adminState\":\"In Service\",\"nonDfsUsedSpace\":4227534848,\"capacity\":528293822464,\"numBlocks\":991,\"version\":\"3.2.1\",\"used\":133592313856,\"remaining\":363614490624,\"blockScheduled\":0,\"blockPoolUsed\":133592313856,\"blockPoolUsedPercent\":25.287502,\"volfails\":0,\"lastBlockReport\":95}}";
        JSONObject jsonObject = JSONUtil.parseObj(json);
        for (String key : jsonObject.keySet()) {
            JSONObject node = jsonObject.getJSONObject(key);

            if (key.startsWith("xmqyrz06")) {
                key = "19.82.92.37";
            } else if (key.startsWith("xmqyrz08")) {
                key = "19.82.92.39";
            } else if (key.startsWith("xmqyrz08")) {
                key = "19.82.92.39";
            } else if (key.startsWith("xmqyrz09")) {
                key = "19.82.92.40";
            } else if (key.startsWith("xmqyrz11")) {
                key = "19.82.92.42";
            } else if (key.startsWith("xmqyrz12")) {
                key = "19.82.92.43";
            }
            System.out.println(key + " : " + node.getLong("blockPoolUsed") / 1000000 + "MB");
        }
    }
}
