package license;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.prefs.Preferences;


public class CreateLicense {
	private static final Logger logger = LoggerFactory.getLogger(CreateLicense.class);

	//common param
	private static String PRIVATEALIAS = "";
	private static String KEYPWD = "";
	private static String STOREPWD = "";
	private static String SUBJECT = "";
	private static String licPath = "";
	private static String priPath = "";
	//license content
	private static String issuedTime = "";
	private static String notBefore = "";
	private static String notAfter = "";
	private static String consumerType = "";
	private static int consumerAmount = 0;
	private static String info = "";

	private final static X500Principal X_500_PRINCIPAL = new X500Principal(
			"CN=yzl、OU=bocloud、O=bocloud、C=CN");

	public void setParam(String propertiesPath) {
		// 获取参数
		Properties prop = new Properties();
		InputStream in = getClass().getClassLoader().getResourceAsStream(propertiesPath);
		try {
			prop.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		PRIVATEALIAS = prop.getProperty("PRIVATEALIAS");
		KEYPWD = prop.getProperty("KEYPWD");
		STOREPWD = prop.getProperty("STOREPWD");
		SUBJECT = prop.getProperty("SUBJECT");
		KEYPWD = prop.getProperty("KEYPWD");
		licPath = prop.getProperty("licPath");
		priPath = prop.getProperty("priPath");
		//license content
		issuedTime = prop.getProperty("issuedTime");
		notBefore = prop.getProperty("notBefore");
		notAfter = prop.getProperty("notAfter");
		consumerType = prop.getProperty("consumerType");
		consumerAmount = Integer.valueOf(prop.getProperty("consumerAmount"));
		info = prop.getProperty("info");

	}

	public boolean create() {
		try {
			LicenseContent content = createLicenseContent();
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

	private static LicenseParam initLicenseParam() {
		Preferences preference = Preferences.userNodeForPackage(CreateLicense.class);
		CipherParam cipherParam = new DefaultCipherParam(STOREPWD);

		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
				CreateLicense.class, priPath, PRIVATEALIAS, STOREPWD, KEYPWD);
		LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
				preference, privateStoreParam, cipherParam);
		return licenseParams;
	}

	public final static LicenseContent createLicenseContent() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		LicenseContent content = new LicenseContent();
		content.setSubject(SUBJECT);
		content.setHolder(X_500_PRINCIPAL);
		content.setIssuer(X_500_PRINCIPAL);

		try {
			content.setIssued(format.parse(issuedTime));
			content.setNotBefore(format.parse(notBefore));
			content.setNotAfter(format.parse(notAfter));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		content.setConsumerType(consumerType);
		content.setConsumerAmount(consumerAmount);
		content.setInfo(info);

		content.setExtra(new Object());
		return content;
	}
}
