package com.pass.util.font;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * @author yuanzhonglin
 * @date 2020/7/2
 * @Description:
 */
public class FontUtil {
    public static void main(String[] args) throws Exception {
        URL fontUrl = FontUtil.class.getClassLoader().getResource("font/simsun.ttc");
        File file = new File(fontUrl.getFile());
        FileInputStream stream = new FileInputStream(file);
        Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
        System.out.println(font.getName());
    }
}
