package com.pass.util.inetwork;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * 获取当前系统桌面路径
 *
 * @Author yuanzhonglin
 * @since 2019/4/30
 */
public class PathUtil {

    /**
     * 获取当前系统桌面路径
     *
     * @Author yuanzhonglin
     * @since 2019/4/30
     */
    public static String getDeskPath () {
        File deskDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String deskPath = deskDir.getAbsolutePath();
        return deskPath;
    }
}
