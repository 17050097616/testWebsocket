package com.a.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test() throws Exception {
        String iv = "jlYljdfOhfFyDFnbPYDFAmbtDFABSOytdfafa";

        String ikey = "SdfayOdfMNhDFASssfYTfbaapsfuIOfdfasnga";
//        EquipmentDB db = new EquipmentDB();
//        db.Mac = DeviceUtils.getMacAddress();
//        db.Sn = DeviceUtils.getAndroidID();
//        String des = new String(EncryptUtils.encryptDES2Base64(GsonUtil.GsonString(db).getBytes("utf-8"), ikey.getBytes(), "DES/CBC/PKCS5Padding", iv.getBytes()));
//        LogUtils.i(des);
        System.out.println(iv.getBytes().length);
        System.out.println(ikey.substring(0,32).getBytes("utf-8").length);
    }
}