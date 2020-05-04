package com.example.newclient;

import android.util.Log;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
class MyClientEndpoint  {

    private Session session1;
    private String MessageFromServer = "Hi";
    private boolean stopThread  ;
    private static MyClientEndpoint instance = new MyClientEndpoint(); // Eagerly Loading of single ton instance

    private MyClientEndpoint(){
        // private to prevent anyone else from instantiating
    }

    public static MyClientEndpoint getInstance(){
        return instance;
    }

    void ConnectClientToServer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              connectToWebSocket();
            }
            });
        thread.start();
    }


    @OnOpen
    public void onOpen(Session session){
        session1 = session ;

    }

    public Session getSession1() {
        return session1;
    }

    @OnMessage
    public void onMessage(String incomingMessage){
        MessageFromServer = incomingMessage ;
    }

    public String getMessageFromServer() {
        return MessageFromServer;
    }

    private  void connectToWebSocket() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            URI uri = URI.create("ws://e37c53fd.ngrok.io/mavenjavafxserver/chat");
        try {
            session1 = container.connectToServer(this, uri);
            if(session1 != null){

                Log.d("TAG" , "Connected to Session: "+ session1.getId());
            }
        } catch (DeploymentException e) {
            e.printStackTrace();

            Log.e("ERROR" , "Deployment Exception");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR" , "IO Exception");
        }
    }
}




