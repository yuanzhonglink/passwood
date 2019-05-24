package com.pass.util.proto;

import com.google.protobuf.DescriptorProtos;
import com.pass.common.RunTimeUtil;
import com.pass.util.inetwork.GlobalConstants;
import com.pass.util.inetwork.PathUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 解析proto<br>
 * 需要安装protoc,配置环境变量<br>
 * </p>
 * @author yuanzhonglin
 * @date 2019/5/24
 */
@Slf4j
public class ParserProtoUtil {

    // 解析proto
    public static boolean parserProto(String protoPath) {
        boolean parseFlag = true;
        try {
            String descPath = protoPath.replaceAll("\\.proto", ".desc");

            String cmd = "protoc -I=" + PathUtil.getFileDir(protoPath)
                    + " --include_source_info --descriptor_set_out="
                    + descPath + " " + protoPath;
            Runtime run = RunTimeUtil.getRunTimeUtil();
            Process p = null;

            if (GlobalConstants.IS_WINDOWS) {
                p = run.exec(new String[]{"cmd", "/c", cmd});
            } else {
                p = run.exec(new String[]{"/bin/sh", "-c", cmd});
            }

            // 失败
            if (p.waitFor() != 0 || p.exitValue() == 1) {
                if (p.exitValue() == 1) {
                    parseFlag = false;
                    BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    String line = "";
                    log.info("proto解析错误信息：");
                    while ((line = in.readLine()) != null) {
                        log.info(line);
                    }
                }
            }
        } catch (Exception e) {
            parseFlag = false;
            log.error("解析proto文件失败：" + e.getMessage());
        }
        return parseFlag;
    }

    // 读取解析后proto文件
    public static void getProtoContent(String descPath) {
        try {
            // 解析desc文件，获取proto文件中的自己扩展
            DescriptorProtos.FileDescriptorSet fdSet = DescriptorProtos.FileDescriptorSet
                    .parseFrom(new FileInputStream(descPath));
            List<DescriptorProtos.FileDescriptorProto> fdp = fdSet.getFileList();
            Iterator<DescriptorProtos.FileDescriptorProto> iterator = fdp.iterator();
            while (iterator.hasNext()) {
                DescriptorProtos.FileDescriptorProto proto = iterator.next();
                log.info("content:" + proto.toString());
            }
        } catch (Exception e) {
            log.error("proto解析文件获取异常：" + e.getMessage());
        }
    }

    // 测试
    public static void main(String[] args) {
        String file_name = "proto/zorkcolddata.proto";
        String path = PathUtil.getProFilePath(file_name);

        // 解析
//        parserProto(path);

        // 读取
        String descPath = path.replaceAll("\\.proto", ".desc");
        getProtoContent(descPath);
    }
}
