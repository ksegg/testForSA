package com.example.service;

import com.example.constant.Constants;
import com.example.constant.MessageConstants;
import com.example.entity.User;
import com.example.mapper.LoginUserMapper;
import com.example.util.AESCryptUntil;
import com.example.util.RequestContextHolderUtil;
import com.example.vo.UserVo;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 【名称】</br>
 * ログイン用Service
 *
 * @author eptsz01
 */
@Service
public class LoginUserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(LoginUserService.class);

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Resource
    AESCryptUntil aesCryptUntil;

    /**
     * 【名称】</br>
     * ログインユーザ取得
     *
     * @param loginScreen
     * @return
     */
    public UserVo getUserInfo(String loginScreen) {

        String login = aesCryptUntil.queryableEncrypt(loginScreen);
        // Loginでユーザ情報を取得します。
        List<com.example.entity.User> resultList = this.loginUserMapper.getLoginUser(login);

        if (resultList.isEmpty()) {
            // もし取得できないの場合、Nullを戻します。
            return null;
        } else {
            User u = resultList.get(0);
            UserVo uv = dozerBeanMapper.map(u, UserVo.class);
            uv.setLoginIdPage(aesCryptUntil.queryableDecrypt(uv.getLoginId()));
            uv.setEmailPage(aesCryptUntil.queryableDecrypt(uv.getEmail()));
            uv.setNamePage(aesCryptUntil.queryableDecrypt(uv.getName()));
            return uv;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String loginScreen) throws UsernameNotFoundException {
        UserVo user = this.getUserInfo(loginScreen);
        UserDetails userDetails = null;
        // requestを取得
        HttpSession httpSession = RequestContextHolderUtil.getSession();
        Locale locale = RequestContextHolderUtil.getRequest().getLocale();
        String Msg1 = messageSource.getMessage(MessageConstants.D001_MSG_E_001, null, locale);
        String Msg2 = messageSource.getMessage(MessageConstants.D001_MSG_E_002, null, locale);

        if (user == null) {
            // ユーザが存在しません
            httpSession.setAttribute(Constants.SYS_001, Msg1);
            throw new UsernameNotFoundException(Msg1);
        }
        // ユーザが無効のエラー
        if (user.getIsInvalid() != 0) {
            httpSession.setAttribute(Constants.SYS_001, Msg2);
            throw new UsernameNotFoundException(Msg2);
        }
        // ユーザが5回以上の場合
        if (user.getTrialNo() == 5) {
            httpSession.setAttribute(Constants.SYS_001, Msg2);
            throw new UsernameNotFoundException(Msg2);
        }

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        String strRole = Constants.ROLE_USER;
        switch (user.getRole()) {
            case 1:
                strRole = "ROLE_" + Constants.ROLE_SYSTEM;
                break;
            default:
                strRole = "ROLE_" + Constants.ROLE_USER;
                break;
        }

        authList.add(new SimpleGrantedAuthority(strRole));

        userDetails = new org.springframework.security.core.userdetails.User(loginScreen, user.getCryptedPassword(), true, true, true, true, authList);
        return userDetails;
    }

}
