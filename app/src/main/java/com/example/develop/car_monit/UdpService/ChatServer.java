package com.example.develop.car_monit.UdpService;

import android.util.Log;

import com.example.develop.car_monit.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Jimmy Liang on 2018/7/13.
 */

public class ChatServer extends Thread {

    private DatagramSocket server = null;
    private static final int PORT = 8000; //接收的Port號，要與傳送方的Packet port號相等

    public ChatServer() throws IOException {
        //設定Socket接收的Port號，要與傳送方的DatagramPacket第四個參數的port號相等
        server = new DatagramSocket(PORT);
        Log.d("User", "new server socket");
    }

    public void run() {

        //設定接收資料用的新byte物件，長度設為65535
        byte[] byte65535 = new byte[65535];
        //接收資料用的DatagramPacket參數分別放入用來存放訊息的空元件和接收長度，如超過接收長度則會閹割資料
        DatagramPacket dPacket = new DatagramPacket(byte65535, 10000);
        String txt;

        try {
            Log.d("User", "runing run()");
            while (true) {
                //持續接收資料，如沒有資料則會卡在此等待接收到後才會繼續執行下去
                server.receive(dPacket);
                while (true) {
                    //印出來到螢幕上
                    txt = new String(byte65535, 0, dPacket.getLength());
                    MainActivity.exHandler.sendMessage(MainActivity.exHandler.obtainMessage(1, txt));
                    Log.d("User", "Handler send Message");
                    if (true) break;
                }
                //CloseSocket(client);//關閉
            }
        } catch (IOException e) {
        }
    }

    private void CloseSocket(DatagramSocket socket) throws IOException {
        socket.close();
    }
}
