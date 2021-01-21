package com.accloud;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hive.jdbc.HiveQueryResultSet;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guoyu.huang
 * @since 2020-12-25
 */
public class Hive {

    private static final Logger log = LoggerFactory.getLogger(Hive.class);

    private static final String HIVE_DRIVER = "org.apache.hive.jdbc.HiveDriver";
    //    private static String HIVE_URL = "jdbc:hive2://192.168.12.14:10000/default";
    private static String HIVE_URL = "jdbc:hive2://19.82.92.43:10000/xm_eis";
    private static final String HIVE_USER = "xm_eis";
    private static final String HIVE_PASSWORD = "Xm-e1s3930";
    private static final String HIVE_CREATE_TABLE_SCRIPT = "/home/xm_eis/bigdata/hive.sql";
    private static final String HIVE_INIT_DATA_DIRECTORY = "/tmpdir/data/";
    private static final String HIVE_DATA_DIRECTORY = "/home/xm_eis/bigdata/data/";
    private static final String DATA_DELETE_TABLE = "data_delete";

    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        log.info("开始执行-------------------> main");
        if (args.length > 0 && "query".equals(args[0])) {
            if (args.length > 2) {
                query(args[1]);
            }
        } else {
            batch();
        }
    }

    /**
     * @param entname
     */
    private static void query(String entname) {
        // 获取表字段的映射关系
        log.info("开始执行-------------------> query");
        JSONObject jsonObject = new JSONObject(ColumnConstants.COLUMN_JSON);
        Connection conn = null;
        try {
            Class.forName(HIVE_DRIVER);
            conn = DriverManager.getConnection(HIVE_URL, HIVE_USER, HIVE_PASSWORD);
            Statement stmt = conn.createStatement();

            String tableName = "e_baseinfo";
            String hql = "select * from " + tableName + " where entname = '" + entname + "'";
            String pripid = executeQuery(hql, jsonObject.getJSONObject(tableName), stmt);
            log.info("----------------->> " + pripid);
            List<ColumnConstants.TableAndHql> tableAndHqlList = ColumnConstants.buildHql(entname, pripid);
            log.info("----------------->> 待执行HQL数量：" + tableAndHqlList.size());
            tableAndHqlList.forEach(tableAndHql -> {
                try {
                    executeQuery(tableAndHql.getHql(), jsonObject.getJSONObject(tableAndHql.getTableName()), stmt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            log.error("处理发生异常，异常原因：" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行查询
     *
     * @param hql
     * @param tableJson
     * @param stmt
     * @return
     * @throws SQLException
     */
    private static String executeQuery(String hql, JSONObject tableJson, Statement stmt) throws SQLException {

        log.info("----------------->> hql : " + hql);
        HiveQueryResultSet hiveQueryResultSet = (HiveQueryResultSet) stmt.executeQuery(hql);
        String pripid = null;
        while (hiveQueryResultSet.next()) {
            log.info("----------------->> 存在结果，解析中");
            if (hql.contains("e_baseinfo")) {
                pripid = hiveQueryResultSet.getString("pripid");
            }
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < tableJson.keySet().size(); i++) {
                String columnName = hiveQueryResultSet.getMetaData().getColumnName(i + 1);
                Object columnValue = hiveQueryResultSet.getObject(columnName);
                if (columnName.contains("\\.")) {
                    columnName = tableJson.getString(columnName.split("\\.")[1]);
                } else {
                    columnName = tableJson.getString(columnName);
                }

                content.append(columnName + "：" + columnValue + "@@@");
            }
            log.info("解析后的内容：" + content.toString());
        }
        return pripid;
    }

    /**
     * 批量任务
     */
    private static void batch() {
        log.info("开始执行-------------------> batch");
        ResultSet res = null;
        Connection conn = null;
        try {
            Class.forName(HIVE_DRIVER);
            conn = DriverManager.getConnection(HIVE_URL, HIVE_USER, HIVE_PASSWORD);
            Statement stmt = conn.createStatement();
            // 判断是否存在企业基础信息表
            try {
                stmt.executeQuery("desc e_baseinfo");
                // 执行增量数据处理
                loadData(stmt);
            } catch (Exception e) {
                if (e.getMessage().indexOf("Table not found e_baseinfo") > -1) {
                    log.info("开始执行初始化建表语句");
                    if (createTable(stmt)) {
                        log.info("建表成功, 执行初始化存量数据");
                        initData(stmt);
                    } else {
                        log.error("建表失败");
                    }
                }
            }
        } catch (Exception e) {
            log.error("处理发生异常，异常原因：" + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 建表函数
     *
     * @param stmt
     * @throws IOException
     */
    private static boolean createTable(Statement stmt) throws IOException {
        File file = new File(HIVE_CREATE_TABLE_SCRIPT);
        if (file.exists()) {
            String sql = FileUtils.readFileToString(file, "UTF-8");
            String[] sqls = sql.split(";");
            boolean globalResult = true;
            for (int i = 0; i < sqls.length; i++) {
                if (globalResult && StringUtils.isNoneBlank(sqls[i])) {
                    globalResult = execHiveSQL(stmt, sqls[i]);
                } else {
                    execHiveSQL(stmt, sqls[i]);
                }
            }
            return globalResult;
        } else {
            log.error("建表语句文件没有找到");
            return false;
        }
    }

    /**
     * 初始化存量数据
     *
     * @param stmt
     * @return
     */
    private static boolean initData(Statement stmt) {
        File files = new File(HIVE_INIT_DATA_DIRECTORY);
        if (files.exists()) {
            File[] dataFiles = files.listFiles();
            List<String> fileNameList = new ArrayList<>(dataFiles.length);
            for (int i = 0; i < dataFiles.length; i++) {
                String fileName = dataFiles[i].getName();
                fileNameList.add(fileName);
            }
            List<String> failList = new ArrayList<>(dataFiles.length);
            List<String> successList = new ArrayList<>(dataFiles.length);
            // 遍历数据
            for (String fileName : fileNameList) {
                int end = fileName.indexOf(".txt");
                String tableName = fileName.substring(0, end);
                String fullName = HIVE_INIT_DATA_DIRECTORY + fileName;
                String sql = "load data local inpath '" + fullName + "' overwrite into table " + tableName;
                boolean result = execHiveSQL(stmt, sql);
                if (result) {
                    successList.add(fileName);
                } else {
                    failList.add(fileName);
                }
            }

//            successList.forEach(fileName -> {
//                File file = new File(HIVE_INIT_DATA_DIRECTORY + fileName);
//                if (file.exists()) {
//                    file.delete();
//                }
//            });

            if (failList.size() > 0) {
                log.error("*******************************************");
                log.error("初始化存量数据存在失败情况：" + failList.stream().collect(Collectors.joining(",")));
                log.error("*******************************************");
                return false;
            } else {
                return true;
            }
        } else {
            log.error("文件目录不存在");
            return false;
        }
    }

    /**
     * 加载增量数据文件
     *
     * @return
     */
    private static boolean loadData(Statement stmt) {


        File files = new File(HIVE_DATA_DIRECTORY);
        if (files.exists()) {
            File[] dataFiles = files.listFiles();
            log.info("开始执行增量文件，数量为：" + dataFiles.length + "----------------->");
            List<String> addFileNameList = new ArrayList<>(dataFiles.length);
            List<String> deleteFileNameList = new ArrayList<>(dataFiles.length);
            List<String> errorFileNameList = new ArrayList<>(dataFiles.length);
            // 对文件进行排序，把增加的数据放前面，删除放后面
            for (int i = 0; i < dataFiles.length; i++) {
                String fileName = dataFiles[i].getName();
                if (fileName.indexOf("_0.hive") > -1) {
                    deleteFileNameList.add(fileName);
                } else if (fileName.indexOf(".hive") > -1) {
                    addFileNameList.add(fileName);
                } else {
                    errorFileNameList.add(fileName);
                }
            }

            // 开始执行
            List<String> failList = new ArrayList<>(dataFiles.length);
            List<String> successList = new ArrayList<>(dataFiles.length);

            log.info("开始执行增量文件的增加文件，数量为：" + addFileNameList.size() + "----------------->");
            // 排序执行增加
            Collections.sort(addFileNameList);
            for (String fileName : addFileNameList) {
                String hive = ".hive";
                int end = fileName.indexOf(hive);
                String tableName = fileName.substring(9, end).toLowerCase();
                String fullName = HIVE_DATA_DIRECTORY + fileName;
                String sql = "load data local inpath '" + fullName + "' into table " + tableName;
                boolean result = execHiveSQL(stmt, sql);
                if (result) {
                    successList.add(fileName);
                } else {
                    failList.add(fileName);
                }
            }

            log.info("开始执行增量文件的删除文件，数量为：" + deleteFileNameList.size() + "----------------->");
            // 排序执行删除
            Collections.sort(deleteFileNameList);
            for (String fileName : deleteFileNameList) {
                String deleteHive = "_0.hive";
                String fullName = HIVE_DATA_DIRECTORY + fileName;
                String sql = "load data local inpath '" + fullName + "' overwrite into table " + DATA_DELETE_TABLE;
                boolean result = execHiveSQL(stmt, sql);
                if (result) {
                    // 执行删除
                    int end = fileName.indexOf(deleteHive);
                    String tableName = fileName.substring(9, end).toLowerCase();
                    sql = "insert overwrite table " + tableName + " select * from " + tableName + " where s_ext_sequence not in (select table_id from " + DATA_DELETE_TABLE + ")";
                    result = execHiveSQL(stmt, sql);
                    if (result) {
                        successList.add(fileName);
                    } else {
                        failList.add(fileName);
                    }
                }
            }

            log.info("增量文件的增加文件和删除文件执行完成，成功总数为：" + successList.size() + "，失败总数为：" + failList.size() + "----------------->");

            log.info("增量文件的异常文件，数量为：" + errorFileNameList.size() + "----------------->");

            if (failList.size() > 0) {
                log.error("*******************************************");
                log.error("执行增量数据存在失败情况：" + failList.stream().collect(Collectors.joining(",")));
                log.error("*******************************************");
            } else {
                log.error("*******************************************");
                log.error("执行增量数据全部成功");
                log.error("*******************************************");
            }

            successList.forEach(fileName -> {
                File file = new File(HIVE_DATA_DIRECTORY + fileName);
                if (file.exists()) {
                    file.delete();
                }
            });
        } else {
            log.error("文件不存在");
        }
        return false;
    }

    /**
     * 执行hiveSQL
     *
     * @param stmt
     * @param sql
     * @return
     */
    private static boolean execHiveSQL(Statement stmt, String sql) {
        try {
            log.info("execute：" + sql + "");
            stmt.execute(sql);
            log.info("execute success");
            return true;
        } catch (SQLException e) {
            log.info("执行hiveSql：" + sql + "，失败原因：" + e.getMessage());
            return false;
        }
    }
}
