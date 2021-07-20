package com.example.entity;
import lombok.Data;

/**
 * ユーザ情報Entity
 * 
 * @author eptsz01
 *
 */
@Data
public class User {
    // ユーザID
    private Integer id;
    // ログインID
    private String loginId;
    // 名前
    private String name;
    // メールアドレス
    private String email;
    // 電場番号
    private String tel;
    // ロールID
    private Integer role;
    // 無効フラグ
    private Integer isInvalid;
    // 試行回数
    private Integer trialNo;
    // パスワード
    private String cryptedPassword;
    // アクセス元のIPアドレス
    private String remoteAddr;
    // 最終ログイン日時
    private String lastLoginAt;
    // 作成者ID
    private String createdBy;
    // 作成日時
    private String createdAt;
    // 更新者ID
    private String updatedBy;
    // 更新日時
    private String updatedAt;

}
