package com.bera.todo.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2Config {

  @Bean
  public Server h2TcpServer() throws SQLException {
    return Server.createTcpServer().start();
  }

  @Bean
  public Server h2WebServer() throws SQLException {
    return Server.createWebServer().start();
  }
}
