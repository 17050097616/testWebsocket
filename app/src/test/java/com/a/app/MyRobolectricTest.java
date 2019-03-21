package com.a.app;

import com.blankj.utilcode.util.EncodeUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

//import org.junit.runner.RunWith;

/**
 * Created by chm on 2018/4/9.
 */
@RunWith(RobolectricTestRunner.class) //Robolectric 相关，看不懂的话忽略
//@Config(constants = BuildConfig.class,
//        manifest = "src/main/AndroidManifest.xml")
//@Config(manifest=Config.NONE)
//@Config(constants = BuildConfig.class)
@Config(constants = BuildConfig.class
        , manifest = Config.NONE)
public class MyRobolectricTest {

    @Test
    public void test() throws Exception {
    }

    @Test
    public void test1() throws Exception {
        String deCode = new String(EncodeUtils.base64Decode("eyJNc2dUeXBlIjoxLCJNc2ciOiLorr7lpIfnmbvlvZUiLCJVc2VyIjpudWxsfQ=="));
        System.out.println(deCode);
    }

}
