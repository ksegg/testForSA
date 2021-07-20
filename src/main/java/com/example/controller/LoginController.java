package com.example.controller;

import com.example.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * 【名称】</br>
 * ログインコントロール 【説明】</br>
 * 登録画面のコントロールです。
 * 
 * @author eptsz01
 *
 */
@Controller
public class LoginController {

    // log
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 【名称】</br>
     * 登録ボタン処理</br>
     * 【説明】</br>
     * 登録のコントロールですが、具体の登録ロジックはsecurityモジュールに実施している。
     *
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {

        // ログインエラーがあるか
        String LoginMsg = (String) session.getAttribute(Constants.SYS_001);
        if (StringUtils.hasLength(LoginMsg)) {
            // エラーがある場合、画面上にセットする
            model.addAttribute(Constants.SYS_001, LoginMsg);
            session.removeAttribute(Constants.SYS_001);
        }
        
        return "login";
    }

    /**
     * 【名称】</br>
     * ログアウト処理</br>
     * 【説明】</br>
     * 具体の登録ロジックはsecurityモジュールに実施している。
     * 
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession request, Model model) {

        return "login";
    }

}
