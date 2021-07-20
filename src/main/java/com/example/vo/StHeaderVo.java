package com.example.vo;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class StHeaderVo implements Serializable {
    // ユーザID
    private Integer id;

    // left
    @NotEmpty(message = "Please input left column data!")
    @Size(max = 50, message = "Please input left column within 50 digits!")
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
