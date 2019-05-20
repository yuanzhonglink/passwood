package com.pass.util.license.common;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * license管理类
 *
 * @Author yuanzhonglin
 * @since 2019/4/30
 */
public class LicenseManagerHolder {
	private static LicenseManager licenseManager;

	public static synchronized LicenseManager getLicenseManager(LicenseParam licenseParams) {
		if (licenseManager == null) {
			licenseManager = new LicenseManager(licenseParams);
		}
		return licenseManager;
	}
}
