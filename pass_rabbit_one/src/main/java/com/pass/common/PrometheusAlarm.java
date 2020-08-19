package com.pass.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2020/8/19
 * @Description:
 */
public class PrometheusAlarm {

    private String version;
    private String groupKey;
    private String status;
    private String receiver;
    private Map<String, Object> groupLabels;
    private Map<String, Object> commonLabels;
    private Map<String, Object> commonAnnotations;
    private String externalURL;
    private List<PrometheusAlert> alerts;

    public PrometheusAlarm() {
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Map<String, Object> getGroupLabels() {
        return this.groupLabels;
    }

    public void setGroupLabels(Map<String, Object> groupLabels) {
        this.groupLabels = groupLabels;
    }

    public Map<String, Object> getCommonLabels() {
        return this.commonLabels;
    }

    public void setCommonLabels(Map<String, Object> commonLabels) {
        this.commonLabels = commonLabels;
    }

    public Map<String, Object> getCommonAnnotations() {
        return this.commonAnnotations;
    }

    public void setCommonAnnotations(Map<String, Object> commonAnnotations) {
        this.commonAnnotations = commonAnnotations;
    }

    public String getExternalURL() {
        return this.externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public List<PrometheusAlarm.PrometheusAlert> getAlerts() {
        return this.alerts;
    }

    public void setAlerts(List<PrometheusAlarm.PrometheusAlert> alerts) {
        this.alerts = alerts;
    }

    public class PrometheusAlert {
        private String status;
        private Map<String, Object> labels;
        private Map<String, Object> annotations;
        @JSONField(
                format = "yyyy-MM-dd'T'HH:mm:ss"
        )
        private Date startsAt;
        @JSONField(
                format = "yyyy-MM-dd'T'HH:mm:ss"
        )
        private Date endsAt;
        private String generatorURL;

        public PrometheusAlert() {
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Map<String, Object> getLabels() {
            return this.labels;
        }

        public void setLabels(Map<String, Object> labels) {
            this.labels = labels;
        }

        public Map<String, Object> getAnnotations() {
            return this.annotations;
        }

        public void setAnnotations(Map<String, Object> annotations) {
            this.annotations = annotations;
        }

        public Date getStartsAt() {
            return this.startsAt;
        }

        public void setStartsAt(Date startsAt) {
            this.startsAt = startsAt;
        }

        public Date getEndsAt() {
            return this.endsAt;
        }

        public void setEndsAt(Date endsAt) {
            this.endsAt = endsAt;
        }

        public String getGeneratorURL() {
            return this.generatorURL;
        }

        public void setGeneratorURL(String generatorURL) {
            this.generatorURL = generatorURL;
        }
    }

}
