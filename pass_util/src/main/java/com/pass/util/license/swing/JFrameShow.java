package com.pass.util.license.swing;

import com.pass.util.inetwork.GlobalConstants;
import com.pass.util.date.DateUtils;
import com.pass.util.license.util.CreateLicense;
import com.pass.util.math.MathUtils;
import com.pass.util.string.StringUtils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static com.pass.util.date.DateUtils.STANDARD_DATETIME_FORMAT;

/**
 * @author yuanzhonglin
 * @date 2019/5/5
 * @Description:
 */
public class JFrameShow {
    /**
     * 创建界面
     *
     * @Author yuanzhonglin
     * @since 2019/4/30
     */
    public static void create() {
        // 创建及设置窗口
        JFrame frame = new JFrame("License证书生成");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //显示在屏幕中央
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dm = tk.getScreenSize();
        frame.setLocation((int) (dm.getWidth() - frame.getWidth()) / 2, (int) (dm.getHeight() - frame.getHeight()) / 2);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);

        // 内部布局
        setContent(mainPanel, frame);

        frame.setVisible(true);
    }

    private static void setContent(JPanel mainPanel, JFrame frame) {
        // -------- 到期时间 --------
        JPanel dataPannel = new JPanel();
        mainPanel.add(dataPannel, BorderLayout.CENTER);

        dataPannel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        dataPannel.setLayout(new GridBagLayout());

        // 字段描述
        JLabel timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        timeLabel.setText("证书过期时间：");
        GridBagConstraints timeLabelConstraints = new GridBagConstraints();
        timeLabelConstraints.anchor = GridBagConstraints.EAST;
        timeLabelConstraints.insets = new Insets(10, 15, 10, 15);
        timeLabelConstraints.gridy = 0;
        timeLabelConstraints.gridx = 0;
        dataPannel.add(timeLabel, timeLabelConstraints);

        // 输入框
        JTextField timeTextField = new JTextField();
        timeTextField.setColumns(40);
        GridBagConstraints timeTextConstraints = new GridBagConstraints();
        timeTextConstraints.anchor = GridBagConstraints.CENTER;
        timeTextConstraints.insets = new Insets(10, 15, 10, 15);
        timeTextConstraints.gridy = 0;
        timeTextConstraints.gridx = 1;
        dataPannel.add(timeTextField, timeTextConstraints);

        // 选择时间按钮
        JButton timeButton = new JButton("选择日期");
        GridBagConstraints timeButtonConstraints = new GridBagConstraints();
        timeButtonConstraints.anchor = GridBagConstraints.WEST;
        timeButtonConstraints.insets = new Insets(10, 15, 10, 15);
        timeButtonConstraints.gridy = 0;
        timeButtonConstraints.gridx = 2;
        dataPannel.add(timeButton, timeButtonConstraints);

        // 监听
        timeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = new Point(700,300);
                JCalendarChooser chooser = new JCalendarChooser(frame, "日期", p, 100);
                Calendar d = chooser.showCalendarDialog();
                String time = DateUtils.dateToString(d.getTime(), STANDARD_DATETIME_FORMAT);
                timeTextField.setText(time);
            }
        });
        // -------- 到期时间 --------

        // -------- 节点数 --------
        JLabel nodeNumLabel = new JLabel();
        nodeNumLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        nodeNumLabel.setText("节点数:");
        GridBagConstraints nodeNumLabelConstraints = new GridBagConstraints();
        nodeNumLabelConstraints.anchor = GridBagConstraints.EAST;
        nodeNumLabelConstraints.insets = new Insets(10, 15, 10, 15);
        nodeNumLabelConstraints.gridy = 1;
        nodeNumLabelConstraints.gridx = 0;
        dataPannel.add(nodeNumLabel, nodeNumLabelConstraints);

        JTextField nodeNumTextField = new JTextField();
        nodeNumTextField.setColumns(40);
        GridBagConstraints nodeNumTextConstraints = new GridBagConstraints();
        nodeNumTextConstraints.anchor = GridBagConstraints.CENTER;
        nodeNumTextConstraints.insets = new Insets(10, 15, 10, 15);
        nodeNumTextConstraints.gridy = 1;
        nodeNumTextConstraints.gridx = 1;
        dataPannel.add(nodeNumTextField, nodeNumTextConstraints);

        // 下方的按钮面板
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(50);

        JPanel buttonPanel = new JPanel(flowLayout);
        buttonPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton createButton = new JButton("生成证书");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createButtonEvent(timeTextField, nodeNumTextField);
            }
        });

        buttonPanel.add(createButton);
    }

    private static void createButtonEvent(JTextField timeTextField, JTextField nodeNumTextField) {
        String afterTime = timeTextField.getText();
        if (StringUtils.isEmpty(afterTime)) {
            JOptionPane.showMessageDialog(null, "证书过期时间不能为空");
            timeTextField.requestFocus();
            return;
        }
        if (!DateUtils.isDate(afterTime, STANDARD_DATETIME_FORMAT)) {
            JOptionPane.showMessageDialog(null, "证书过期时间输入框请填写合法的日期型字符串");
            timeTextField.requestFocus();
            return;
        }
        Date time = DateUtils.stringToDate(afterTime, STANDARD_DATETIME_FORMAT);
        if (time.compareTo(new Date()) < 0) {
            JOptionPane.showMessageDialog(null, "证书过期时间不能小于当前时间");
            timeTextField.requestFocus();
            return;
        }

        String nodeNumText = nodeNumTextField.getText();
        if (StringUtils.isEmpty(nodeNumText)) {
            JOptionPane.showMessageDialog(null, "节点数不能为空");
            nodeNumTextField.requestFocus();
            return;
        }
        if (!MathUtils.isInteger(nodeNumText)) {
            JOptionPane.showMessageDialog(null, "节点数输入框请填写[整数]");
            nodeNumTextField.requestFocus();
            return;
        }

        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setDialogTitle("选择证书保存的文件夹...");

        String dirPath = System.getProperty("user.home");
        if (GlobalConstants.IS_WINDOWS) {
            dirPath = dirPath + File.separator + "Desktop";
        }
        File dir = new File(dirPath);
        jfc.setCurrentDirectory(dir);

        jfc.showDialog(null, "确定");

        File file = jfc.getSelectedFile();
        String path = file.getPath();

        CreateLicense instance = new CreateLicense();
        instance.setParam(afterTime, path);
        boolean success = instance.create(nodeNumText);
        JOptionPane.showMessageDialog(null, instance.getMessage());
        if (success) {
            System.exit(0);
        }
    }

    //重新设置默认字体
    static {
        Font defaultFont = new Font("宋体", Font.PLAIN, 14);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("ToggleButton.font", defaultFont);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("CheckBox.font", defaultFont);
        UIManager.put("RadioButton.font", defaultFont);
        UIManager.put("TabbedPane.font", defaultFont);
        UIManager.put("TitledBorder.font", defaultFont);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("List.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("TextArea.font", defaultFont);
        UIManager.put("TableHeader.font", defaultFont);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("ToolTip.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
        UIManager.put("ScrollPane.font", defaultFont);
        UIManager.put("Menu.font", defaultFont);
        UIManager.put("MenuItem.font", defaultFont);
        UIManager.put("CheckBoxMenuItem.font", defaultFont);
        UIManager.put("RadioButtonMenuItem.font", defaultFont);
    }

}
