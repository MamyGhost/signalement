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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PersonalMessageController {

  private final PersonalMessageSender pushSender;

  public PersonalMessageController(PersonalMessageSender pushSender) {
    this.pushSender = pushSender;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void register(@RequestParam("token") String token) {
    System.out.println("register: " + token);
    this.pushSender.addToken(token);
    this.pushSender.sendPushMessages();
  }

  @PostMapping("/unregister")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unregister(@RequestParam("token") String token) {
    System.out.println("unregister: " + token);
    this.pushSender.removeToken(token);
  }
  
}
