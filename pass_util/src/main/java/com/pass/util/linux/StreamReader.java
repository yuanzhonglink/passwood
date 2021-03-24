package com.pass.util.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 命令行读取工具
 */
public class StreamReader extends Thread {
    private InputStream is;
    private String type;
    private StringBuilder msgBuffer;

    public StreamReader(InputStream is, String type, StringBuilder msgBuffer) {
        this.is = is;
        this.type = type;
        this.msgBuffer = msgBuffer;
    }

    public void run() {
        try {
            // a special property which gets used for: Command line arguments
            // see: http://happygiraffe.net/blog/2009/09/24/java-platform-encoding/
            String charsetName = System.getProperty("sun.jnu.encoding");

            InputStreamReader isr = new InputStreamReader(is, charsetName);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                msgBuffer.append(line);
                msgBuffer.append("\n");
            }

            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
