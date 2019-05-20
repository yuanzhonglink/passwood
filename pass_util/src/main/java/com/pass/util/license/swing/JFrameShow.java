package com.pass.util.license.swing;

import com.pass.util.date.DateUtils;
import com.pass.util.license.create.CreateLicense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 居中显示
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,3,5,15));
        // 添加面板
        frame.add(panel);
        // 内部布局
        setContent(panel, frame);
        // 窗口大小不可变
        frame.setResizable(false);
        // 显示窗口
        frame.pack();
        frame.setVisible(true);
    }

    private static void setContent(JPanel panel, JFrame frame) {
        // 到期时间
        JLabel maturityLabel = new JLabel("到期时间:");
        maturityLabel.setFont(new Font("楷体",Font.BOLD,17));
        panel.add(maturityLabel);

        JTextField maturityText = new JTextField(20);
        maturityText.setFont(new Font("楷体",Font.PLAIN,17));
        panel.add(maturityText);

        JButton timeButton = new JButton("选择日期");
        panel.add(timeButton);

        timeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = new Point(700,300);
                JCalendarChooser chooser = new JCalendarChooser(frame,"日期",p,100);
                Calendar d = chooser.showCalendarDialog();
                String time = DateUtils.dateToString(d.getTime(), STANDARD_DATETIME_FORMAT);
                maturityText.setText(time);
            }
        });

        // 节点数
        JLabel nodeNumLabel = new JLabel("节点数:");
        nodeNumLabel.setFont(new Font("楷体",Font.BOLD,16));
        panel.add(nodeNumLabel);

        JTextField nodeNumText = new JTextField(20);
        panel.add(nodeNumText);
        nodeNumText.setFont(new Font("楷体",Font.PLAIN,16));


        // 生成按钮
        JButton button = new JButton("生成证书");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                jfc.showDialog(new JLabel(), "保存");

                File file = jfc.getSelectedFile();

                CreateLicense cLicense = new CreateLicense();
                cLicense.setParam(maturityText.getText(), file.getPath());
                cLicense.create(nodeNumText.getText());
                System.exit(0);
            }
        });
        panel.add(button);
    }

}
