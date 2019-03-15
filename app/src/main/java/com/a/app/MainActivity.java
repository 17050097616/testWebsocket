package com.a.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View viewById = findViewById(R.id.tv1);

        ExampleClient c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        try {
            c = new ExampleClient( new URI( "ws://localhost:8887" ));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        c.connect();

    }
}
