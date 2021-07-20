package com.example.handle;

import com.example.constant.Constants;
import com.example.mapper.LoginUserMapper;
import com.example.service.LoginUserService;
import com.example.util.AESCryptUntil;
import com.example.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 【名称】</br>
 * Login成功時の処理
 *
 * @author eptsz01
 */
@Component
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    // log
    private final Logger logger = LoggerFactory.getLogger(MyAuthenctiationSuccessHandler.class);

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private LoginUserService loginUserService;

    @Resource
    AESCryptUntil aesCryptUntil;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 成功の場合、ログイン者の情報をSessionに保存します。
        String loginScreen = request.getParameter("login");
        String login = aesCryptUntil.queryableEncrypt(loginScreen);

        UserVo u = loginUserService.getUserInfo(loginScreen);

        if (u != null) {
            // 登録成功したら、試回数をクリアする
            loginUserMapper.clearTrialNo(login, getIpAddress(request));

            request.getSession().setAttribute(Constants.SYS_002, u);
        }
        response.sendRedirect("/home");
    }

    /**
     * ユーザのipアドレスを取得する
     *
     * @param request
     * @return XFor
     */
    private static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasLength(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {

            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.hasLength(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (!StringUtils.hasLength(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasLength(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasLength(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
}
