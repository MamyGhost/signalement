/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;


/**
 *
 * @author Mamitiana
 */
@Service
public class FcmClient {

  public FcmClient(FcmSettings settings) {
    //Path p = Paths.get(System.getProperty("user.dir")+settings.getServiceAccountFile());
    Path p = Paths.get(settings.getServiceAccountFile());
    System.out.println("Present Project Directory : "+ p.toAbsolutePath());
    try (InputStream serviceAccount = Files.newInputStream(p.toAbsolutePath())) {
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

      FirebaseApp.initializeApp(options);
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
  
  public void sendPersonalMessage(String clientToken, Map<String, String> data)
      throws InterruptedException, ExecutionException {
    AndroidConfig androidConfig = AndroidConfig.builder()
        .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey("personal")
        .setPriority(Priority.HIGH)
        .setNotification(AndroidNotification.builder().setTag("personal").build())
        .build();

    ApnsConfig apnsConfig = ApnsConfig.builder()
        .setAps(Aps.builder().setCategory("personal").setThreadId("personal").build())
        .build();

    Message message = Message.builder().putAllData(data).setToken(clientToken)
        .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
        .setNotification(Notification.builder().setTitle("Personal Message")
            .setBody("A Personal Message").build())
        .build();

    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    System.out.println("Sent message: " + response);
  }
  
}
