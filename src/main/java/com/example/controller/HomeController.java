package com.example.controller;

import com.example.service.SettingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Resource
    private SettingService settingService;

    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {

        return "home";
    }
}
