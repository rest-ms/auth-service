package it.siletto.ms.auth;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AppConfiguration extends Configuration {
  @Valid
  @NotNull
  @JsonProperty
  private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

  @JsonProperty
  private String proxyHost = null;

  @JsonProperty
  private int proxyPort = 8080;

  @JsonProperty
  private HashMap<String, String> adminUsers = null;

  @JsonProperty
  private String privateKeyFile = null;

  @JsonProperty
  private String publicKeyFile = null;


  public JerseyClientConfiguration getJerseyClientConfiguration() {
    return httpClient;
  }

  public Map<String, String> getAdminUsers() {
      return adminUsers;
  }
  
  public String getProxyHost() {
      return proxyHost;
  }
  
  public int getProxyPort() {
      return proxyPort;
  }

public String getPrivateKeyFile() {
	return privateKeyFile;
}

public String getPublicKeyFile() {
	return publicKeyFile;
}
}
