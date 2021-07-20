package com.example.constant;

import java.util.ResourceBundle;

/**
 * 【名称】 </br>
 * システム用定数定義 【説明】 </br>
 * 該当システムの定数を定義するクラス
 *
 * @author eptsz01
 */
public class Constants {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("config.application");

    /**
     * 暗号化キー
     */
    public static final String SECREAT_KEY = resourceBundle.getString("secreat-key");

    /**
     * 権限定数：テナント一般
     */
    public static final String ROLE_USER = "USER";

    /**
     * 権限定数：システム管理者
     */
    public static final String ROLE_SYSTEM = "SYSTEM";

    /**
     * ブランク
     */
    public static final String BLANK = "";

    /**
     * カンマ区切り
     */
    public static final String KANMA = ",";

    public static final String SYS_001 = "sys_001";

    public static final String SYS_002 = "sys_002";
}
