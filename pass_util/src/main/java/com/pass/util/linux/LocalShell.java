package com.pass.util.linux;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 调用当前机器命令行执行命令
 */
public class LocalShell {
    public static final Properties SYSTEM_PROPERTIES = System.getProperties();
    public static final String OS_NAME = SYSTEM_PROPERTIES.getProperty("os.name");
    /**
     * 文件分隔符
     * <p>
     * Linux下为/ <br>
     * Windows下为\ <br>
     * </p>
     */
    public static final String FILE_SEPARATOR = SYSTEM_PROPERTIES.getProperty("file.separator");


    /**
     * 执行
     */
    public static Map<String, Object> exec(String commandText) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        try {
            Runtime rt = Runtime.getRuntime();
            Process p;
            boolean isWindows;

            if (OS_NAME.indexOf("Windows") >= 0
                    && FILE_SEPARATOR.equals("\\")) {
                isWindows = true;
            } else {
                isWindows = false;
            }

            if (isWindows) {
                p = rt.exec(new String[]{"cmd", "/c", commandText});
            } else {
                p = rt.exec(new String[]{"/bin/sh", "-c", commandText});
                // If the -c option is present, then commands are read from string.
            }

            StringBuilder errorBuffer = new StringBuilder();
            StringBuilder outputBuffer = new StringBuilder();

            if (p.getErrorStream() != null) {
                StreamReader errorReader = new StreamReader(p.getErrorStream(), "Error", errorBuffer);
                //errorReader.start();
                errorReader.run();
            }

            if (p.getInputStream() != null) {
                StreamReader outputReader = new StreamReader(p.getInputStream(), "Output", outputBuffer);
                //outputReader.start();
                outputReader.run();
            }

            int exitValue = p.waitFor(); // 当前线程等待，一直要等到由 Process对象表示的进程已经终止

            boolean isSuccess;

            if (exitValue == 0) {
                isSuccess = true;
            } else {
                isSuccess = false;
            }

            result.put("isSuccess", isSuccess);
            result.put("errorMsg", errorBuffer.toString());
            result.put("exitValue", exitValue);
            result.put("successMsg", outputBuffer.toString());
        } catch (Exception e) {
            result.put("isSuccess", false);
            result.put("errorMsg", e.getMessage());
            result.put("exitValue", -999);
            result.put("successMsg", null);
        }

        return result;
    }
}