package com.invenia.erpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ErpServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ErpServiceApplication.class, args);
  }

}
