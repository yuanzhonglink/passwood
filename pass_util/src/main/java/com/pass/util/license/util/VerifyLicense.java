package com.pass.util.license.util;

import com.alibaba.fastjson.JSONObject;
import com.pass.util.date.DateUtils;
import com.pass.util.encrypt.DesEncryptUtils;
import com.pass.util.license.common.LicenseManagerHolder;
import com.pass.util.license.entity.LicenseExtraEntity;
import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * 校验证书
 *
 * @Author yuanzhonglin
 * @since 2019/4/30
 */
public class VerifyLicense {
    private static final Logger logger = LoggerFactory.getLogger(VerifyLicense.class);

    private static String PUBLICALIAS = "";
    private static String STOREPWD = "";
    private static String SUBJECT = "";
    private static String pubPath = "";

    private static final String PROPERTIES_PATH = "license/public-param.properties";

    public static final String STATUS = "status";
    public static final String NOT_AFTER = "notAfter";
    public static final String EXTRA = "extra";

    public static Map<String, String> verify(File file) {
        Map<String, String> map = new HashMap<>();
        map.put(STATUS, "false");
        LicenseManager licenseManager = LicenseManagerHolder.getLicenseManager(initLicenseParams());
        try {
            licenseManager.install(file);
            LicenseContent verify = licenseManager.verify();

            LicenseExtraEntity extra = JSONObject.parseObject(verify.getExtra().toString(), LicenseExtraEntity.class);
            String time = DateUtils.dateToString(verify.getNotAfter(), DateUtils.STANDARD_DATETIME_FORMAT);

            if (extra != null) {
                map.put(STATUS, "true");
                map.put(NOT_AFTER, time);
                map.put(EXTRA, extra.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("license证书文件获取异常：" + e.getMessage());
        }
        return map;
    }

    private static LicenseParam initLicenseParams() {
        Properties prop = new Properties();
        InputStream in = VerifyLicense.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
        try {
            prop.load(in);
        } catch (IOException e) {
            logger.error("license配置文件获取异常：" + e.getMessage());
        }
        PUBLICALIAS = prop.getProperty("PUBLICALIAS");
        STOREPWD = DesEncryptUtils.decrypt(prop.getProperty("STOREPWD"));
        SUBJECT = prop.getProperty("SUBJECT");
        pubPath = prop.getProperty("pubPath");

        Preferences preference = Preferences.userNodeForPackage(VerifyLicense.class);
        CipherParam cipherParam = new DefaultCipherParam(STOREPWD);

        KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
                VerifyLicense.class, pubPath, PUBLICALIAS, STOREPWD, null);
        LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
                preference, privateStoreParam, cipherParam);
        return licenseParams;
    }
}
