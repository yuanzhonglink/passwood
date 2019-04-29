package license;


public class LicenseCreateTest {
	public static void main(String[] args){
		CreateLicense cLicense = new CreateLicense();
		cLicense.setParam("license/param.properties");
		boolean success = cLicense.create();
		System.out.println(success);
	}
}
