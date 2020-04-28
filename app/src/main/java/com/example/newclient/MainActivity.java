package com.example.newclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import javax.websocket.Session;

public class MainActivity extends AppCompatActivity {

    EditText editText ;
    String message = "Hello";
    TextView textView3 ;
    TextView textView4 ;
    TextView textView6;
    MyClientEndpoint myClientEndpoint  = new MyClientEndpoint();
    Session session ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        editText = (EditText) findViewById(R.id.editText2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setText("Not Connected To Server");
        textView6 = (TextView) findViewById(R.id.textView6) ;
        textView6.setText("");

    }



    public void send(View view) {

        message = editText.getText().toString();

        myClientEndpoint.SendMessageToServer(message, textView6);

    }
    public void retrieve(View view){

        myClientEndpoint.WriteMessageFromServer(textView3,textView6);

    }





    public void onClickbutton(View view)  {

        session = myClientEndpoint.ConnectClientToServer(textView4);
        //textView4.setText(MyClientEndpoint.session.getId());

    }



}





