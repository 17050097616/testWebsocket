package com.a.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements WebsocketListener {

    private TextView showTv;
    private ExampleClient c;

    private static String iv = "jlYljdfOhfFyDFnbPYDFAmbtDFABSOytdfafa";

    private static String ikey = "SdfayOdfMNhDFASssfYTfbaapsfuIOfdfasnga";
    private static String UTF = "utf-8";

    private boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.tv1);
        showTv = (TextView) findViewById(R.id.showTv);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
                try {
                    c = new ExampleClient(new URI("ws://ar.zhizhentang.com:10023/equipment"), MainActivity.this);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                c.connect();
            }
        });


    }

    public void show(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showTv.setText(msg);
            }
        });
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        c.send("ping=\"ping\"");
        first = true;
    }

    @Override
    public void onMessage(String message) {
        show(message);
        if (first && "Pong".equals(message)) {
            first = false;
            EquipmentDB db = new EquipmentDB();
            db.Mac = DeviceUtils.getMacAddress();
            try {
                db.Sn = DeviceUtils.getAndroidID();
                String des = new String(EncryptUtils.encryptDES(GsonUtil.GsonString(db).getBytes(UTF), ikey.substring(0,32).getBytes(UTF),
                        "DES/CBC/PKCS5Padding", iv.getBytes(UTF)));
                LogUtils.i(des);
                c.send(des);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            if (!"Pong".equals(message)) {
                LogUtils.i(message);
                EquipMsg equipMsg = GsonUtil.GsonToBean(message, EquipMsg.class);
                if (equipMsg != null && equipMsg.MsgType == MsgType.Login) {
                    show("login success");
                } else {
                    assert equipMsg != null;
                    show(equipMsg.MsgType + "");
                }
            } else {
                show(message);
            }
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        show("Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {

    }

    private static byte[] symmetricTemplate(final byte[] data,
                                            final byte[] key,
                                            final String algorithm,
                                            final String transformation,
                                            final byte[] iv,
                                            final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKey secretKey;
            if ("DES".equals(algorithm)) {
                DESKeySpec desKey = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
                secretKey = keyFactory.generateSecret(desKey);
            } else {
                secretKey = new SecretKeySpec(key, algorithm);
            }
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
            }
            cipher.getBlockSize();
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
