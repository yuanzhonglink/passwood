package com.pass.util.license.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/**
 * 2010-7-9 下午09:42:10
 * <p>
 * 日期选择器<br>
 * 使用方法: 1.创建JCalendarChooser对象;<br>
 * 2.调用showCalendarDialog()方法获取选择的Calendar对象
 *
 * @author mkk(monkeyking1987@126.com)
 * @version 1.0
 * @see JFileChooser
 */
public class JCalendarChooser extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     */
    public JCalendarChooser(Frame parent) {
        super(parent, true);
        this.setTitle(title);
        // init
        this.initDatas();
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     * @param title
     *            标题
     */
    public JCalendarChooser(Frame parent, String title) {
        super(parent, title, true);
        // init
        this.initDatas();
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     * @param title
     *            标题
     * @param location
     *            界面显示的位置坐标
     */
    public JCalendarChooser(Frame parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        // init
        this.initDatas();
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     * @param title
     *            标题
     * @param location
     *            界面显示的位置坐标
     * @param showYears
     *            显示的年数值,默认为显示100年,即前后50年<br>
     *            比如 当前年份为2010年,参数showYears为30年,则界面显示的年份下拉框值从1995-2024<br>
     *            注意: 若showYears值必须大于0,否则使用默认年数值
     */
    public JCalendarChooser(Frame parent, String title, Point location,
                            int showYears) {
        super(parent, title, true);
        this.location = location;
        if (showYears > 0) {
            this.showYears = showYears;
        }
        // init
        this.initDatas();
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     */
    public JCalendarChooser(Dialog parent) {
        super(parent, true);
        this.setTitle(title);
        // init
        this.initDatas();
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     * @param title
     *            标题
     */
    public JCalendarChooser(Dialog parent, String title) {
        super(parent, title, true);
        // init
        this.initDatas();
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     * @param title
     *            标题
     * @param location
     *            界面显示的位置坐标
     */
    public JCalendarChooser(Dialog parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        // init
        this.initDatas();
    }

    /**
     * 构造器<br>
     * 在创建对象后调用showCalendarDialog()方法获取选择的日期值
     *
     * @param parent
     *            父组件
     * @param title
     *            标题
     * @param location
     *            界面显示的位置坐标
     * @param showYears
     *            显示的年数值,默认为显示100年,即前后50年<br>
     *            比如 当前年份为2010年,参数showYears为30年,则界面显示的年份下拉框值从1995-2024<br>
     *            注意: 若showYears值必须大于0,否则使用默认年数值
     */
    public JCalendarChooser(Dialog parent, String title, Point location,
                            int showYears) {
        super(parent, title, true);
        this.setTitle(title);
        this.location = location;
        if (showYears > 0) {
            this.showYears = showYears;
        }
        // init
        this.initDatas();
    }

    /**
     *
     * @param width
     *            长度
     * @param height
     *            宽度
     * @return
     */
    private Dimension getStartDimension(int width, int height) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim.width = dim.width / 2 - width / 2;
        dim.height = dim.height / 2 - height / 2;
        return dim;
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        this.chooser = this;
        this.mdcEvent = new MouseDoubleClickedEvent();
        this.calendar = Calendar.getInstance();
        this.year1 = this.calendar.get(Calendar.YEAR);
        this.month1 = this.calendar.get(Calendar.MONTH);
        this.day1 = this.calendar.get(Calendar.DAY_OF_MONTH);
        this.years = new String[showYears];
        this.months = new String[12];
        // init label1
        label1 = new JLabel();
        label1.setForeground(Color.MAGENTA);
        // init months
        for (int i = 0; i < this.months.length; i++) {
            this.months[i] = " " + formatDay(i + 1);
        }
        // init years
        int start = this.year1 - this.showYears/2;
        for (int i = start; i < start + showYears; i++) {
            this.years[i - start] = String.valueOf(i);
        }
        // show info
        this.setInfo(this.year1 + "-" + this.formatDay(this.month1 + 1) + "-"
                + formatDay(this.day1) + "            ", Color.BLUE);

    }

    /**
     * 显示Dialog的方法
     */
    private void showDialog() {
        // 设置布局
        setLayout(new BorderLayout());
        // 北面面板
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        showNorthPanel(panel3);
        add(panel3, BorderLayout.NORTH);
        // 中间面板
        add(printCalendar(), BorderLayout.CENTER);
        // 南边面板
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.showSouthPanel(panel2);
        add(panel2, BorderLayout.SOUTH);
        // 设置大小
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // 设置显示的位置
        if (this.location == null) {
            Dimension dim = getStartDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            setLocation(dim.width, dim.height);
        } else {
            setLocation(this.location);
        }
        setVisible(true);
    }

    /**
     * 显示北面面板
     *
     * @param panel
     */
    private void showNorthPanel(JPanel panel) {
        this.button2 = new JButton("上一月");
        this.button2.setToolTipText("上一月");
        this.button2.addActionListener(this);
        panel.add(this.button2);
        this.comboBox1 = new JComboBox(this.years);
        this.comboBox1.setSelectedItem(String.valueOf(year1));
        this.comboBox1.setToolTipText("年份");
        this.comboBox1.setActionCommand("year");
        this.comboBox1.addActionListener(this);
        panel.add(this.comboBox1);
        this.comboBox2 = new JComboBox(this.months);
        this.comboBox2.setSelectedItem(" " + formatDay(month1 + 1));
        this.comboBox2.setToolTipText("月份");
        this.comboBox2.setActionCommand("month");
        this.comboBox2.addActionListener(this);
        panel.add(this.comboBox2);
        this.button3 = new JButton("下一月");
        this.button3.setToolTipText("下一月");
        this.button3.addActionListener(this);
        panel.add(this.button3);
    }

    /**
     * 显示南边面板信息
     *
     * @param panel
     */
    private void showSouthPanel(JPanel panel) {
        // 状态栏
        panel.add(label1);
        this.button1 = new JButton("确定");
        this.button1.setToolTipText("确定");
        this.button1.addActionListener(this);
        panel.add(button1);
        panel.add(new JLabel(" "));
    }

    /**
     * 在构造对象后调用此方法获取选择的日历值
     *
     * @return 选择日历对象Calendar
     */
    public Calendar showCalendarDialog() {
        this.showDialog();
        return this.calendar;
    }

    /**
     * 状态栏显示
     *
     * @param info
     */
    private void setInfo(String info, Color c) {
        if (this.label1 != null && c != null) {
            this.label1.setText(info);
            this.label1.setForeground(c);
        }
    }

    /**
     * 设置显示的数字,若小于10则在前面加0
     *
     * @param day
     * @return
     */
    private String formatDay(int day) {
        if (day < 10) {
            return "0" + day;
        }
        return String.valueOf(day);
    }

    /**
     * 输出日期的面板
     *
     * @return
     */
    private JPanel printCalendar() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 7, 0, 0));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        // 将日期设为当月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 获取第一天是星期几
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        // 打印标头
        JButton b = null;
        for (int i = 0; i < tits.length; i++) {
            b = new JButton("<html><b>" + tits[i] + "</b></html>");
            b.setForeground(Color.BLACK);
            b.setBackground(Color.WHITE);
            b.setBorder(BorderFactory.createEmptyBorder());
            b.setEnabled(false);
            panel.add(b);
        }
        int count = 0;
        for (int i = Calendar.SUNDAY; i < weekDay; i++) {
            b = new JButton(" ");
            b.setEnabled(false);
            panel.add(b);
            count++;
        }
        int currday = 0;
        String dayStr = null;
        do {
            currday = calendar.get(Calendar.DAY_OF_MONTH);
            dayStr = formatDay(currday);
            // 日,月,年相等则显示
            if (currday == day1 && month1 == month2 && year1 == year2) {
                b = new JButton("[" + dayStr + "]");
                b.setForeground(Color.RED);
            } else {
                b = new JButton(dayStr);
                b.setForeground(Color.BLUE);
            }
            count++;
            b
                    .setToolTipText(year2 + "-" + formatDay(month2 + 1) + "-"
                            + dayStr);
            b.setBorder(BorderFactory.createEtchedBorder());
            b.addActionListener(this);
            b.addMouseListener(this.mdcEvent);
            panel.add(b);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            // 循环完成时月份实际上已经加1
        } while (calendar.get(Calendar.MONTH) == month2);
        // 减1,保持为当前月
        this.calendar.add(Calendar.MONTH, -1);
        if (!flag) {
            // 设置日期为当天
            this.calendar.set(Calendar.DAY_OF_MONTH, this.day1);
            flag = true;
        }
        for (int i = count; i < 42; i++) {
            b = new JButton(" ");
            b.setEnabled(false);
            panel.add(b);
        }
        return panel;
    }

    /**
     * 内部类,用于监听日期按钮的双击事件 2010-7-10 下午11:34:26
     */
    private class MouseDoubleClickedEvent extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (MouseEvent.BUTTON1 == e.getButton()) {
                if (e.getClickCount() == 2) {
                    // 鼠标左键双击事件
                    JButton b = (JButton) e.getSource();
                    String s = b.getText();
                    if (s.matches("^\\d+$")) {
                        int day = Integer.parseInt(s);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                    } else if (s.startsWith("[")) {
                        calendar.set(Calendar.DAY_OF_MONTH, day1);
                    }
                    // dispose
                    chooser.dispose();
                }
            }
        }

    }

    /**
     * 更新界面
     */
    private void updatePanel() {
        this.remove(this.panel);
        this.add(this.printCalendar(), BorderLayout.CENTER);
        this.validate();
    }

    /**
     * 响应点击事件
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("下一月".equals(command)) {
            // 1.月份加一
            this.calendar.add(Calendar.MONTH, 1);
            // 2.更新显示的年与月
            int year5 = calendar.get(Calendar.YEAR);
            // 判断是否超出显示的最大范围
            int maxYear = this.year1 + this.showYears/2-1;
            if (year5 > maxYear) {
                this.calendar.add(Calendar.MONTH, -1);
                this.setInfo("年份越界: [" + year5 + " > " + maxYear + "]      ",
                        Color.RED);
                return;
            }
            int month5 = calendar.get(Calendar.MONTH) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(year5));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            // 3.更新界面
            this.updatePanel();
        } else if ("上一月".equals(command)) {
            // 1.月份减一
            this.calendar.add(Calendar.MONTH, -1);
            // 2.更新显示的年与月
            int year5 = calendar.get(Calendar.YEAR);
            // 判断是否超出显示的最大范围
            int minYear = this.year1 - this.showYears/2;
            if (year5 < minYear) {
                this.calendar.add(Calendar.MONTH, 1);
                this.setInfo("年份越界: [" + year5 + " < " + minYear + "]      ",
                        Color.RED);
                return;
            }
            int month5 = calendar.get(Calendar.MONTH) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(year5));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            // 3.更新界面
            this.updatePanel();
        } else if ("确定".equals(command)) {
            chooser.dispose();
        } else if (command.matches("^\\d+$")) {
            // 选择一个具体的日期的事件
            int day9 = Integer.parseInt(command);
            this.calendar.set(Calendar.DAY_OF_MONTH, day9);
            String str = this.calendar.get(Calendar.YEAR) + "-"
                    + this.formatDay(this.calendar.get(Calendar.MONTH) + 1)
                    + "-"
                    + this.formatDay(this.calendar.get(Calendar.DAY_OF_MONTH));
            this.setInfo(str + "            ", getRandomColor());
        } else if (command.startsWith("[")) {
            // 处理为当前日期的情况
            this.calendar.set(Calendar.DAY_OF_MONTH, this.day1);
            String str = this.calendar.get(Calendar.YEAR) + "-"
                    + this.formatDay(this.calendar.get(Calendar.MONTH) + 1)
                    + "-"
                    + this.formatDay(this.calendar.get(Calendar.DAY_OF_MONTH));
            this.setInfo(str + "            ", getRandomColor());
        } else if ("year".equalsIgnoreCase(command)) {
            // 选择年事件
            int value = Integer.parseInt(this.comboBox1.getSelectedItem()
                    .toString().trim());
            this.calendar.set(Calendar.YEAR, value);
            this.updatePanel();
        } else if ("month".equalsIgnoreCase(command)) {
            // 选择月事件
            int value = Integer.parseInt(this.comboBox2.getSelectedItem()
                    .toString().trim());
            this.calendar.set(Calendar.MONTH, value - 1);
            this.updatePanel();
        }
    }

    /**
     * 获取一个随机的颜色值
     *
     * @return
     */
    private Color getRandomColor() {
        Color c = new Color(0,0,0);
        return c;
    }

    // 默认宽度与高度
    private static final int DEFAULT_WIDTH = 285;
    private static final int DEFAULT_HEIGHT = 280;
    // 默认显示的年份为100年,即当年的前后50年
    private int showYears = 100;
    // 状态栏与确认按钮
    // 状态栏最多放置17个汉字
    private JLabel label1 = null;
    private JButton button1 = null;
    // 上一个月,下一个月按钮
    private JButton button2 = null;
    private JButton button3 = null;
    // 选择年与月的下拉框
    private JComboBox comboBox1 = null;
    private JComboBox comboBox2 = null;
    // 日历对象
    private Calendar calendar = null;
    // 年与月的选择集合对象
    private String[] years = null;
    private String[] months = null;
    // 当前年,月,日
    private int year1, month1, day1;
    private JPanel panel = null;
    private String tits[] = { "日", "一", "二", "三", "四", "五", "六" };
    private String title = "选择日期";
    private Point location = null;
    private MouseDoubleClickedEvent mdcEvent = null;
    private JCalendarChooser chooser = null;
    // 标识字段
    private boolean flag;

}
