package com.pass.util.license;

import com.pass.util.license.swing.JFrameShow;

/**
 * @author yuanzhonglin
 * @date 2019/5/20
 * @Description:
 */
public class LicenseApplication {

    public static void main(String[] args) {
        //  创建license
        JFrameShow.create();

        // 验证license
//        String deskPath = PathUtil.getDeskPath();
//        Map<String, String> verify = VerifyLicense.verify(new File(deskPath + File.separator + "yzl.lic"));
//        System.out.println(verify.toString());
    }
}
