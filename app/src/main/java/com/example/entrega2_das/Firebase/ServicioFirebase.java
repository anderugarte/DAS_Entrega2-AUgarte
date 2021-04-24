package com.example.entrega2_das.Firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServicioFirebase extends FirebaseMessagingService {

    public ServicioFirebase() {

    }

    public void onMessageReceived(RemoteMessage remoteMessage){
        if (remoteMessage.getData().size() > 0){
            // El mensaje contiene datos

        }
        if (remoteMessage.getData() != null) {
            // El mensaje es una notificacion

        }
    }
}
