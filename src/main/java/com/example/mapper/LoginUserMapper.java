package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 【名称】</br>
 * ログインMapper
 * 
 * @author eptsz01
 *
 */
@Mapper
public interface LoginUserMapper {

    /**
     * 【名称】</br>
     * LoginUser情報取得
     * 
     * @param login ログインID
     * @return User userList
     */
    List<User> getLoginUser(String login);

    /**
     * 【名称】</br>
     * 試行回数更新
     * 
     * @param login ログインID
     * @return 更新件数
     */
    int updateTrialNo(String login);

    /**
     * 【名称】</br>
     * 試行回数0に更新,登録者IPなども更新する。
     *
     * @param login ログインID
     * @param remoteAddr IP
     * @return 更新件数
     */
    public int clearTrialNo(String login, String remoteAddr);

}
