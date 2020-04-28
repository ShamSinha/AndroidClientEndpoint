package com.example.newclient;

import android.widget.TextView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;



@ClientEndpoint
class MyClientEndpoint {

    private Session session1;
    private String EchoFromServer ;


    Session ConnectClientToServer(final TextView text) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              connectToWebSocket(text);
            }
            });
        thread.start();
        return session1;
    }

    @OnOpen
    public void onOpen(Session session){
        session1 = session ;
    }

    @OnMessage
    public void onMessage(String incomingMessage){
        EchoFromServer = incomingMessage ;

    }


    private void connectToWebSocket(TextView text) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            URI uri = URI.create("ws://echo.websocket.org");
        try {
            session1 = container.connectToServer(this, uri);
            if(session1 != null){
                text.setText("Connected to Session : \n" + session1.getId());
            }
        } catch (DeploymentException e) {
            e.printStackTrace();
            text.setText("Deployment Exception");
        } catch (IOException e) {
            e.printStackTrace();
            text.setText("IO Exception");
        }

    }

    void SendMessageToServer(final String message, final TextView textView1) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    session1.getBasicRemote().sendText(message);
                    textView1.setText("Message Sent");
                } catch (IOException e) {
                    textView1.setText("IOException in sending Message");
                    e.printStackTrace();
                }

            }
        });
        thread1.start();
    }

    void WriteMessageFromServer(TextView textView, TextView textView1){
        textView1.setText("");
        textView.setText(EchoFromServer);
    }

}



//"ws://echo.websocket.org"

    /*MainActivity m = new MainActivity();

    @OnOpen
    public void onOpen(Session session){
       this.session = session;
    }

    @OnMessage
    public void onMessage(String incomingMessage){
        m.textView3.setText(String.format("%s   %s", session.getId(), incomingMessage));
    }*/



