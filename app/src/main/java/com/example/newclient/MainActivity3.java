package com.example.newclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity3 extends Activity {

    EditText editText3;
    String message3 ;
    TextView textView3write ;
    MyClientEndpoint myClientEndpoint1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        editText3 = (EditText) findViewById(R.id.editText3);

        textView3write = (TextView) findViewById(R.id.textView5);
        myClientEndpoint1 = MyClientEndpoint.getInstance() ;

    }

    @Override
    protected void onResume() {
        super.onResume();
        writemessage2();
    }

    public void send3(View view) {

        message3 = editText3.getText().toString();
       // myClientEndpoint1.SendMessage(message3);

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myClientEndpoint1.getSession1().getBasicRemote().sendText(message3);
                    Log.d("TAG" , "Message Sent" );
                    Log.d("TAG", myClientEndpoint1.getSession1().getId());

                    } catch (IOException ex) {
                    Log.e("ERROR" , "IOException in BasicRemote") ;
                    ex.printStackTrace();
                }
            }
        });
        thread2.start();
    }

    public void Moveto1stActivity(View view){

        Intent i = new Intent(MainActivity3.this ,MainActivity.class);
        startActivity(i);
    }

    public void writemessage2(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    textView3write.setText(myClientEndpoint1.getMessageFromServer()) ;
                }
            }
        });
        thread.start();
    }


}
