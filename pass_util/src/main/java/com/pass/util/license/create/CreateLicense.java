package com.pass.util.license.create;

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

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.prefs.Preferences;

import static com.pass.util.date.DateUtils.STANDARD_DATETIME_FORMAT;

/**
 * 生成证书
 *
 * @Author yuanzhonglin
 * @since 2019/4/30
 */
public class CreateLicense {
	private static final Logger logger = LoggerFactory.getLogger(CreateLicense.class);

	//common param
	private static String PRIVATEALIAS = "";
	private static String KEYPWD = "";
	private static String STOREPWD = "";
	private static String SUBJECT = "";
	private static String licPath = "";
	private static String priPath = "";
	//com.orientsec.grpc.license content
	private static String issuedTime = "";
	private static String notBefore = "";
	private static String notAfter = "";
	private static String consumerType = "";
	private static int consumerAmount = 0;

	private final static X500Principal X_500_PRINCIPAL = new X500Principal(
			"CN=yzl、OU=yzl、O=yzl、C=CN");

	private static final String PROPERTIES_PATH = "license/param.properties";

	public void setParam(String afterTime, String path) {
		Properties prop = new Properties();
		InputStream in = getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH);
		try {
			prop.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		//common param
		PRIVATEALIAS = prop.getProperty("PRIVATEALIAS");
		KEYPWD = DesEncryptUtils.decrypt(prop.getProperty("KEYPWD"));
		STOREPWD = DesEncryptUtils.decrypt(prop.getProperty("STOREPWD"));
		SUBJECT = prop.getProperty("SUBJECT");
		priPath = prop.getProperty("priPath");
		//com.orientsec.grpc.license content
		Date nowTime = new Date();
		issuedTime = DateUtils.dateToString(nowTime, STANDARD_DATETIME_FORMAT);
		notBefore = DateUtils.dateToString(nowTime, STANDARD_DATETIME_FORMAT);
		consumerType = prop.getProperty("consumerType");
		consumerAmount = Integer.valueOf(prop.getProperty("consumerAmount"));
		String fileName = prop.getProperty("licPath");

		notAfter = afterTime;
		licPath = path + File.separator + fileName;
	}

	public boolean create(String nodeNum) {
		try {
			LicenseContent content = createLicenseContent(nodeNum);
			LicenseParam param = initLicenseParam();
			LicenseManager manager = LicenseManagerHolder.getLicenseManager(param);
			manager.store(content, new File(licPath));
		} catch (Exception e) {
			logger.error("证书生成失败", e);
			return false;
		}
		logger.info("生成证书成功");
		return true;
	}

	private static LicenseParam initLicenseParam() throws Exception {
		Preferences preference = Preferences.userNodeForPackage(CreateLicense.class);
		CipherParam cipherParam = new DefaultCipherParam(STOREPWD);

		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
				CreateLicense.class, priPath, PRIVATEALIAS, STOREPWD, KEYPWD);
		LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
				preference, privateStoreParam, cipherParam);
		return licenseParams;
	}

	public final static LicenseContent createLicenseContent(String nodeNum) throws Exception{
		LicenseContent content = new LicenseContent();
		content.setSubject(SUBJECT);
		content.setHolder(X_500_PRINCIPAL);
		content.setIssuer(X_500_PRINCIPAL);
		content.setIssued(DateUtils.stringToDate(issuedTime, STANDARD_DATETIME_FORMAT));
		content.setNotBefore(DateUtils.stringToDate(notBefore, STANDARD_DATETIME_FORMAT));
		content.setNotAfter(DateUtils.stringToDate(notAfter, STANDARD_DATETIME_FORMAT));
		content.setConsumerType(consumerType);
		content.setConsumerAmount(consumerAmount);

		LicenseExtraEntity entity = new LicenseExtraEntity();
		entity.setNodeNum(Integer.parseInt(nodeNum));
		content.setExtra(entity.toString());
		return content;
	}
}
