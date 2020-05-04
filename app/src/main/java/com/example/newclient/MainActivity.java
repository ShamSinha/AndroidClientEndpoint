package com.example.newclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity {

    EditText editText ;
    String message ;
    TextView textView3 ;
    MyClientEndpoint myClientEndpoint ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main2);

        editText = (EditText) findViewById(R.id.editText2);
        textView3 = (TextView) findViewById(R.id.textView3);

        myClientEndpoint = MyClientEndpoint.getInstance() ;


    }

    @Override
    protected void onResume() {
        super.onResume();
        writemessage();
    }

    public void send(View view) {

        message = editText.getText().toString();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myClientEndpoint.getSession1().getBasicRemote().sendText(message);
                    Log.d("TAG" , "Message Sent" );
                    Log.d("TAG", myClientEndpoint.getSession1().getId());


                } catch (IOException e) {
                    Log.e("ERROR" , "IOException in BasicRemote") ;
                    e.printStackTrace();
                }

            }
        });
        thread1.start();

    }

    public void onClickbutton(View view)  {

        myClientEndpoint.ConnectClientToServer();
    }
    public void Moveto2ndActivity(View view){
        Intent i = new Intent(MainActivity.this ,MainActivity3.class);
        startActivity(i);
    }

    public void writemessage(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    textView3.setText(myClientEndpoint.getMessageFromServer()) ;
                }
            }
        });
        thread.start();
    }





}





