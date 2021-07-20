package com.example.handle;

import com.example.constant.Constants;
import com.example.constant.MessageConstants;
import com.example.mapper.LoginUserMapper;
import com.example.util.AESCryptUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 【名称】</br>
 * Login失敗時の処理
 *
 * @author eptsz01
 */
@Component
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    // log
    private final Logger logger = LoggerFactory.getLogger(MyAuthenctiationFailureHandler.class);

    @Autowired
    private LoginUserMapper loginUserMapper;
    @Autowired
    protected MessageSource messageSource;

    @Resource
    AESCryptUntil aesCryptUntil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        String errorMessage = (String) request.getSession().getAttribute(Constants.SYS_001);

        if (!StringUtils.hasLength(errorMessage)) {
            String loginScreen = request.getParameter("login");
            String login = aesCryptUntil.queryableEncrypt(loginScreen);
            String Msg = messageSource.getMessage(MessageConstants.D001_MSG_E_001, null, request.getLocale());
            request.getSession().setAttribute(Constants.SYS_001, Msg);
            // PassWordが存在しません、或いは別の異常がある場合 TRIAL_NO（試行回数）アップ
            loginUserMapper.updateTrialNo(login);
        }

        response.sendRedirect("/login");

    }
}
