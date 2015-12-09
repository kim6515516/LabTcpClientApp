package com.example.kim6515516.labtcpclientapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button bt_send;
    Button bt_connect;
    Button bt_close;

    EditText bt_edit;

    String serverIp = "203.246.112.158";
    int serverPort = 54321;
    Socket socket;
    DataOutputStream out;

    Thread  tConnect;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext = this.getApplicationContext();
        bt_send = (Button) findViewById(R.id.bt_send);
        bt_connect = (Button) findViewById(R.id.bt_con);
        bt_edit = (EditText) findViewById(R.id.et_edit);
        bt_close = (Button) findViewById(R.id.bt_close);


        tConnect =   new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket  = new Socket(serverIp, serverPort);
                    out = new DataOutputStream(socket.getOutputStream());
                    Log.d("lab", "서버연결 완료.");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("lab", "서버연결 에러 .");
                }
            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    out.writeUTF(bt_edit.getText().toString()); // 데이타 서버에 전송하게
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tConnect.start();  // 서버 연결 시작
            }
        });

        bt_close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    socket.close();   // 서버 연결 종료
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
