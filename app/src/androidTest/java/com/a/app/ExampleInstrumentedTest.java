package com.a.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.crypto.Cipher;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.a.app", appContext.getPackageName());
    }

    @Test
    public void test1() throws Exception {
        try{
            String iv = "jlYljdfOhfFyDFnbPYDFAmbtDFABSOytdfafa".substring(0,16);
//            String iv = "jlYljdfO";
//            iv=String.format("%64s",iv);
            String ikey = "SdfayOdfMNhDFASssfYTfbaapsfuIOfdfasnga".substring(0,32);
            EquipmentDB db = new EquipmentDB();
            db.Mac = DeviceUtils.getMacAddress();
            db.Sn = DeviceUtils.getAndroidID();
            String des = new String(EncryptUtils.encryptAES2Base64(GsonUtil.GsonString(db).getBytes("utf-8"), ikey.getBytes("utf-8")
                    , "AES/CBC/PKCS7Padding", iv.getBytes("utf-8")));
            LogUtils.i(des);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void test2() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        System.out.println(cipher.getBlockSize());
//        System.out.println(cipher.getParameters());
        String ikey = "SdfayOdfMNhDFASssfYTfbaapsfuIOfdfasnga".substring(0,32);
        String iv = "jlYljdfOhfFyDFnbPYDFAmbtDFABSOytdfafa".substring(0,16);
        System.out.println(new String(EncryptUtils.decryptBase64AES("askXy1L+cmew6FyyUdX6XYRFceM0wTwYBXYPP9kFFY4boPGe2LOcC3HJNuBPAQvl9+h/1WTvE933mpahYsLlAA==".getBytes("utf-8")
                , ikey.getBytes("utf-8"),"AES/CBC/PKCS7Padding", iv.getBytes("utf-8"))));
    }
}
