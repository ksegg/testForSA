package com.example.vo;

import lombok.Data;

@Data
public class UserVo {
    // ユーザID（システム用）
    private String id;
    // ログインID(暗号化)
    private String loginId;
    // ログインID（画面表示用）
    private String loginIdPage;
    // 名前(暗号化)
    private String name;
    // 名前
    private String namePage;
    // メールアドレス
    private String email;
    // メールアドレス
    private String emailPage;
    // ロール
    private Integer role;
    // 無効フラグ
    private int isInvalid;
    // 試行回数
    private int trialNo;
    // パスワード(暗号化後)
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
