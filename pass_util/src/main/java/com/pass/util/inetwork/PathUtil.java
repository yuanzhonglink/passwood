package com.pass.util.inetwork;

import com.pass.util.string.StringUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;

/**
 * 获取路径
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
        File deskDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String deskPath = deskDir.getAbsolutePath();
        return deskPath;
    }

    /**
     * 获取项目中文件路径
     *
     * @Author yuanzhonglin
     * @since 2019/5/24
     */
    public static String getProFilePath(String fileName) {
        URL url = PathUtil.class.getClassLoader().getResource(fileName);
        String path = url.getPath();
        if (GlobalConstants.IS_WINDOWS) {
            path = path.substring(1);
        }
        return path;
    }

    /**
     * 获取文件所在目录
     *
     * @Author yuanzhonglin
     * @since 2019/5/24
     */
    public static String getFileDir(String fileName) {
        String dirPath = null;
        if (StringUtils.isNotEmpty(fileName)) {
            File file = new File(fileName);
            dirPath = file.getParent();
        }
        return dirPath;
    }
}
