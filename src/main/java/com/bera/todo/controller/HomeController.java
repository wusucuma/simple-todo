package com.bera.todo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController implements ErrorController {

  @GetMapping({"/", "/error"})
  public String home() {
    return "index";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
