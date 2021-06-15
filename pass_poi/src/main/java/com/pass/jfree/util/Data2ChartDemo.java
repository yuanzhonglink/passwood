package com.pass.jfree.util;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2020/6/13
 * @Description:
 */
public class Data2ChartDemo {
    public static void main(String[] args) throws Exception {
        // excel2003工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = wb.createSheet("Sheet 1");
        // 如果不使用Font,中文将显示不出来
        Font font = new Font("新宋体", Font.BOLD, 15);
        // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        // 折线图
        // 创建字节输出流
        ByteArrayOutputStream lineOut = new ByteArrayOutputStream();
        // 获取折线图数据
        Map<String, Map<String, Object>> lineChartData = getLineChartData();
        JFreeChart jFreeChart = Data2Chart.lineChart("宿主机/云主机新增趋势图", lineChartData, "时间", "数量");
        // 读取chart信息至字节输出流
        ChartUtilities.writeChartAsPNG(lineOut, jFreeChart, 600, 300);
        // anchor主要用于设置图片的属性
        HSSFClientAnchor lineAnchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 2, (short) 1, (short) 12, (short) 15);
        // 插入折线图
        patriarch.createPicture(lineAnchor, wb.addPicture(lineOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

        // 饼图
        // 创建字节输出流
        ByteArrayOutputStream pieOut = new ByteArrayOutputStream();
        // 获取饼图数据
        Map<String, Double> pieChartData = getPieChartData();
        JFreeChart piePort = Data2Chart.barChart("租户配额比例", pieChartData, font);
        // 读取chart信息至字节输出流
        ChartUtilities.writeChartAsPNG(pieOut, piePort, 600, 300);
        // anchor主要用于设置图片的属性
        HSSFClientAnchor pieAnchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 2, (short) 16, (short) 12, (short) 30);
        // 插入饼图
        patriarch.createPicture(pieAnchor, wb.addPicture(pieOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

        // 柱状图
        // 创建字节输出流
        ByteArrayOutputStream histogramOut = new ByteArrayOutputStream();
        // 获取饼图数据
        Map<String, Map<String, Object>> histogramData = getHistogramData();
        JFreeChart histogramPort = Data2Chart.histogram("云主机分布图",histogramData,null,null);
        // 读取chart信息至字节输出流
        ChartUtilities.writeChartAsPNG(histogramOut, histogramPort, 600, 300);
        // anchor主要用于设置图片的属性
        HSSFClientAnchor histogramAnchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 2, (short) 31, (short) 12, (short) 45);
        // 插入饼图
        patriarch.createPicture(histogramAnchor, wb.addPicture(histogramOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

        // excel2003后缀
        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\HP\\Desktop\\db_table2excel\\图.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    public static Map<String, Double> getPieChartData() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("已分配额", (double) 10);
        map.put("总分配额", (double) 20);
        return map;
    }

    public static Map<String, Map<String, Object>> getLineChartData() {
        Map<String, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        Map<String, Object> data3 = new HashMap<>();
        Map<String, Object> data4 = new HashMap<>();
        Map<String, Object> data5 = new HashMap<>();

        data1.put("宿主机", 10);
        data2.put("宿主机", 20);
        data3.put("宿主机", 30);
        data4.put("宿主机", 40);
        data5.put("宿主机", 50);

        data1.put("云主机", 50);
        data2.put("云主机", 40);
        data3.put("云主机", 30);
        data4.put("云主机", 20);
        data5.put("云主机", 10);

        //压入数据
        map.put("一月", data1);
        map.put("二月", data2);
        map.put("三月", data3);
        map.put("四月", data4);
        map.put("五月", data5);
        return map;
    }

    public static Map<String, Map<String, Object>> getHistogramData() {
        Map<String, Map<String, Object>> datas =new HashMap<>();

        Map<String, Object> map1=new HashMap<>();
        Map<String, Object> map2=new HashMap<>();
        Map<String, Object> map3=new HashMap<>();

        map1.put("云主机", 10);
        map2.put("云主机", 30);
        map3.put("云主机", 60);

        //压入数据
        datas.put("地区1", map1);
        datas.put("地区2", map2);
        datas.put("地区3", map3);

        return datas;
    }
}
