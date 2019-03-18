package com.a.app;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Created by chm on 2019/3/18.
 */

public interface WebsocketListener {

    void onOpen(ServerHandshake handshakedata);
    void onMessage(String message);
    void onClose(int code, String reason, boolean remote);
    void onError(Exception ex);
}
