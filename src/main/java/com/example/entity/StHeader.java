package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * header情報Entity
 * 
 * @author eptsz01
 *
 */
@Data
public class StHeader implements Serializable {
    // ユーザID
    private Integer id;
    // left
    private String colLeft;
    // center
    private String colCenter;
    // right
    private String colRight;
    // 作成者ID
    private String createdBy;
    // 作成日時
    private String createdAt;
    // 更新者ID
    private String updatedBy;
    // 更新日時
    private String updatedAt;

}
