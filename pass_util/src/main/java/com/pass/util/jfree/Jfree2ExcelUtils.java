package com.pass.util.jfree;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author yuanzhonglin
 * @date 2020/6/13
 * @Description: 导出图表
 */
public class Jfree2ExcelUtils {

    // 折线图
    public static JFreeChart lineChart(String title, Map<String, Map<String, Object>> datas, String type, String unit) throws Exception {
        Font font = new Font("新宋体", Font.BOLD, 16);
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        Set<Map.Entry<String, Map<String, Object>>> set1 = datas.entrySet();
        Iterator iterator1 = set1.iterator();
        Iterator iterator2;
        HashMap<String, Object> map;
        Set<Map.Entry<String, Object>> set2;
        Map.Entry entry1;
        Map.Entry entry2;
        while (iterator1.hasNext()) {
            entry1 = (Map.Entry) iterator1.next();
            map = (HashMap<String, Object>) entry1.getValue();
            set2 = map.entrySet();
            iterator2 = set2.iterator();
            while (iterator2.hasNext()) {
                entry2 = (Map.Entry) iterator2.next();
                ds.setValue(Long.valueOf(entry2.getValue().toString()), entry2.getKey().toString(), entry1.getKey().toString());
            }
        }

        // 创建折线图
        JFreeChart chart = ChartFactory.createLineChart(title, type, unit, ds, PlotOrientation.VERTICAL, true, true, true);
        // 设置整个图片的标题字体
        chart.getTitle().setFont(font);
        // 设置提示条字体
        chart.getLegend().setItemFont(font);
        // 得到绘图区
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        // 设置横轴字体
        plot.getDomainAxis().setTickLabelFont(font);
        // 设置纵轴字体
        plot.getRangeAxis().setTickLabelFont(font);
        // 纵轴数据:最大值x0.5
        plot.getRangeAxis().setUpperMargin(0.5d);
        // 设置纵轴值为Long
        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // 设置纵轴最小值为0
        plot.getRangeAxis().setLowerBound(0);
        // 设置纵轴虚线
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        // 显示纵轴数据
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        return chart;
    }

    // 柱状图
    public static JFreeChart histogram(String title, Map<String, Map<String, Object>> datas, String type, String danwei) throws Exception {
        Font font = new Font("新宋体", Font.BOLD, 16);
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        Set<Map.Entry<String, Map<String, Object>>> set1 = datas.entrySet();
        Iterator iterator1 = set1.iterator();
        Iterator iterator2;
        HashMap<String, Object> map;
        Set<Map.Entry<String, Object>> set2;
        Map.Entry entry1;
        Map.Entry entry2;
        while (iterator1.hasNext()) {
            entry1 = (Map.Entry) iterator1.next();
            map = (HashMap<String, Object>) entry1.getValue();
            set2 = map.entrySet();
            iterator2 = set2.iterator();
            while (iterator2.hasNext()) {
                entry2 = (Map.Entry) iterator2.next();
                ds.setValue(Long.valueOf(entry2.getValue().toString()), entry2.getKey().toString(), entry1.getKey().toString());
            }
        }

        // 创建柱状图
        JFreeChart chart = ChartFactory.createBarChart(title, type, danwei, ds, PlotOrientation.VERTICAL, true, true, true);
        // 设置整个图片的标题字体
        chart.getTitle().setFont(font);
        // 设置提示条字体
        chart.getLegend().setItemFont(font);
        // 绘图区
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        // 横轴
        plot.getDomainAxis().setTickLabelFont(font);
        // 纵轴
        plot.getRangeAxis().setLabelFont(font);
        // 纵轴数据:最大值x0.5
        plot.getRangeAxis().setUpperMargin(0.5d);
        // 设置纵轴虚线
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        // 设置纵轴值为Long
        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // 设置纵轴最小值为0
        plot.getRangeAxis().setLowerBound(0);

        // 显示纵轴数据
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setMaximumBarWidth(0.03);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        return chart;
    }

    // 饼图
    public static JFreeChart barChart(String title, Map<String, Double> datas, Font font) {
        try {
            Set<Map.Entry<String, Double>> set = datas.entrySet();
            DefaultPieDataset pds = new DefaultPieDataset();
            Iterator iterator = set.iterator();
            Map.Entry entry;
            while (iterator.hasNext()) {
                entry = (Map.Entry) iterator.next();
                pds.setValue(entry.getKey().toString(), Double.parseDouble(entry.getValue().toString()));
            }
            // 生成一个饼图的图表：显示图表的标题、组装的数据、是否显示图例、是否生成贴士以及是否生成URL链接
            JFreeChart chart = ChartFactory.createPieChart(title, pds, true, false, true);
            // 设置图片标题的字体
            chart.getTitle().setFont(font);
            // 得到图块,准备设置标签的字体
            PiePlot plot = (PiePlot) chart.getPlot();
            // 设置分裂效果,需要指定分裂出去的key
            plot.setExplodePercent("已分配额", 0.1);
            // 设置标签字体
            plot.setLabelFont(font);
            // 设置图例项目字体
            chart.getLegend().setItemFont(font);
            // 设置开始角度
//            plot.setStartAngle(new Float(3.14f / 2f));  开始角度，意义不大
            //设置plot的前景色透明度
            plot.setForegroundAlpha(0.7f);
            //设置plot的背景色透明度
            plot.setBackgroundAlpha(0.0f);
            //设置标签生成器(默认{0})
            //{0}:key {1}:value {2}:百分比 {3}:sum
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1})/{2}"));  // 一般在{1}后面加单位，如：{0}({1}次)/{2}
            //将内存中的图片写到本地硬盘
//            ChartUtilities.saveChartAsJPEG(new File("H:/a.png"), chart, 600, 300);
            return chart;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
