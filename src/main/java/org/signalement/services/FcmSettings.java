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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "fcm")
@Component
public class FcmSettings {
  private String serviceAccountFile;

  public String getServiceAccountFile() {
    return this.serviceAccountFile;
  }

  public void setServiceAccountFile(String serviceAccountFile) {
    this.serviceAccountFile = serviceAccountFile;
  }

}
