package com.pass.demo.controller;

import com.bocloud.common.http.HttpClient;
import com.bocloud.common.model.Result;
import com.pass.demo.util.JsonMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2020/9/3
 * @Description:
 */
@RestController
@RequestMapping("/alarm")
public class AlarmSendController {

    private static Logger logger = LoggerFactory.getLogger(AlarmSendController.class);

    public static final String ALARM_INFO =
                    "告警信息\n" +
                    "告警名称：${name}\n" +
                    "告警机器：${host}\n" +
                    "告警级别：${level}\n" +
                    "告警内容：${remark}\n" +
                    "触发时间：${time}";

    public static final String ALARM_REC_INFO =
                    "告警恢复信息\n" +
                    "告警名称：${name}\n" +
                    "告警机器：${host}\n" +
                    "原告警级别：${level}\n" +
                    "原告警内容：${remark}\n" +
                    "告警恢复时间：${solveTime}";

    @RequestMapping(value = "/demo", method = {RequestMethod.GET})
    public String demo() {
        return "success";
    }

    @RequestMapping(value = "/send/email", method = {RequestMethod.GET})
    public String email(@RequestParam String host, @RequestParam Long flag) {
        Map map = new HashMap();
        if (flag == 1) {// 恢复告警
            String content  = ALARM_REC_INFO;
            content = content.replace("${name}", "数据库告警");
            content = content.replace("${host}", "127.0.0.1");
            content = content.replace("${level}", "DANGER");
            content = content.replace("${remark}", "连接数大于10次持续10秒，当前值400580608次");
            content = content.replace("${solveTime}", "2020-09-03 09:18:32");

            map.put("text", content);
            map.put("toEmailAddress", "1026256053@qq.com,1026256054@qq.com");
            map.put("subject", "数据库告警");
        } else {// 触发告警
            String content  = ALARM_INFO;
            content = content.replace("${name}", "数据库告警");
            content = content.replace("${host}", "127.0.0.1");
            content = content.replace("${level}", "DANGER");
            content = content.replace("${remark}", "连接数大于10次持续10秒，当前值400580608次");
            content = content.replace("${time}", "2020-09-03 09:18:32");

            map.put("text", content);
            map.put("toEmailAddress", "1026256053@qq.com,1026256054@qq.com");
            map.put("subject", "数据库告警");
        }
        String s = JsonMapperUtils.obj2StringPretty(map);
        logger.info("邮件告警内容："+s);
        String result = sendEmail(map, host);
        return result;
    }

    @RequestMapping(value = "/send/uchat", method = {RequestMethod.GET})
    public String uchat(@RequestParam String host, @RequestParam Long flag) {
        Map map = new HashMap();
        if (flag == 1) {// 恢复告警
            String content  = ALARM_REC_INFO;
            content = content.replace("${name}", "数据库告警");
            content = content.replace("${host}", "127.0.0.1");
            content = content.replace("${level}", "DANGER");
            content = content.replace("${remark}", "连接数大于10次持续10秒，当前值400580608次");
            content = content.replace("${solveTime}", "2020-09-03 09:18:32");

            map.put("content", content);
            map.put("userids", "1026256053,1026256054");
        } else {// 触发告警
            String content  = ALARM_INFO;
            content = content.replace("${name}", "数据库告警");
            content = content.replace("${host}", "127.0.0.1");
            content = content.replace("${level}", "DANGER");
            content = content.replace("${remark}", "连接数大于10次持续10秒，当前值400580608次");
            content = content.replace("${time}", "2020-09-03 09:18:32");

            map.put("content", content);
            map.put("userids", "1026256053,1026256054");
        }
        String s = JsonMapperUtils.obj2StringPretty(map);
        logger.info("u聊告警内容："+s);
        String result = sendUchat(map, host);
        return result;
    }

    public String sendEmail(Map<String, Object> content, String host) {
        String message;
        try {
            HttpClient httpClient = new HttpClient();
            Map<String, Object> header = new HashMap<>();
            header.put("Content-Type", "application/x-www-form-urlencoded");
            String url = "http://" + host + "/deploy/api/vminfo/form/email";
            logger.info("邮件url："+ url);
            Result result = httpClient.post(header, content, url);
            message = JsonMapperUtils.obj2String(result);
            logger.info("告警邮件发送失败:" + message);
        } catch (Exception e) {
            logger.error("发送告警邮件失败:" + e);
            message = "发送告警邮件失败";
        }
        return message;
    }

    public String sendUchat(Map<String, Object> content, String host) {
        String message;
        try {
            HttpClient httpClient = new HttpClient();
            Map<String, Object> header = new HashMap<>();
            header.put("Content-Type", "application/x-www-form-urlencoded");
            String url = "http://" + host + "/deploy/api/vminfo/form/upchat";
            logger.info("u聊url："+ url);
            Result result = httpClient.post(header, content, url);
            message = JsonMapperUtils.obj2String(result);
            logger.info("告警u聊发送失败:" + message);
        } catch (Exception e) {
            logger.error("发送告警u聊失败:" + e);
            message = "发送告警u聊失败";
        }
        return message;
    }
}
