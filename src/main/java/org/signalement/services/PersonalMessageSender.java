/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.services;

/**
 *
 * @author Mamitiana
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PersonalMessageSender {

  private final Set<String> tokenRegistry = new CopyOnWriteArraySet<>();

  private final FcmClient fcmClient;

  private int id = 0;

  public PersonalMessageSender(FcmClient fcmClient) {
    this.fcmClient = fcmClient;
  }

  public void addToken(String token) {
    this.tokenRegistry.add(token);
  }

  public void removeToken(String token) {
    this.tokenRegistry.remove(token);
  }

 // @Scheduled(fixedDelay = 30_000)
  void sendPushMessages() {
    for (String token : this.tokenRegistry) {
      System.out.println("Sending personal message to: " + token);
      Map<String, String> data = new HashMap<>();
      data.put("id", String.valueOf(++this.id));
      data.put("text", String.valueOf(Math.random() * 1000));

      
        try {
            this.fcmClient.sendPersonalMessage(token, data);
        } catch (InterruptedException ex) {
            Logger.getLogger(PersonalMessageSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(PersonalMessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }
     
      
      
    }
  }

}
