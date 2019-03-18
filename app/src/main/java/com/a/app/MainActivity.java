package com.a.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;

import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements WebsocketListener {

    private TextView showTv;
    private ExampleClient c;

    private static String iv = "jlYljdfOhfFyDFnbPYDFAmbtDFABSOytdfafa";

    private static String ikey = "SdfayOdfMNhDFASssfYTfbaapsfuIOfdfasnga";

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
        first=true;
    }

    @Override
    public void onMessage(String message) {
        show(message);
        if (first&&"Pong".equals(message)){
            first=false;
            EquipmentDB db = new EquipmentDB();
            db.Mac = DeviceUtils.getMacAddress();
            try {
                db.Sn = new String(EncryptUtils.encryptDES(DeviceUtils.getAndroidID().getBytes("utf-8"), ikey.getBytes(), "DES", iv.getBytes()), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            c.send(GsonUtil.GsonString(db));
        }else {
            if (!"Pong".equals(message)){
                LogUtils.i(message);
                EquipMsg equipMsg = GsonUtil.GsonToBean(message, EquipMsg.class);
                if (equipMsg!=null&&equipMsg.MsgType==MsgType.Login){
                    show("login success");
                }else {
                    assert equipMsg != null;
                    show(equipMsg.MsgType+"");
                }
            }else{
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
}
