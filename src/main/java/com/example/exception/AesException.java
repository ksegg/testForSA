package com.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 【名称】</br>
 * AES暗号化の異常Exception</br>
 * 
 * @author eptsz01
 *
 */
public class AesException extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger(AesException.class);

    private static final long serialVersionUID = 1L;
    // 異常の戻すコード
    private String retCd;
    // 異常のMessage
    private String msgDes;
    // 異常
    private Exception exception;

    /**
     * 【名称】</br>
     * Aes暗号化異常</br>
     */
    public AesException() {
        super();
    }

    /**
     * 【名称】</br>
     * Aes暗号化異常</br>
     * 
     * @param e
     */
    public AesException(Exception e) {
        super();
        this.exception = e;
    }

    /**
     * 【名称】</br>
     * Aes暗号化異常</br>
     * 
     * @param message
     */
    public AesException(String message) {
        super(message);
        msgDes = message;
    }

    /**
     * 【名称】</br>
     * Aes暗号化異常</br>
     * 
     * @param retCd
     * @param msgDes
     */
    public AesException(String retCd, String msgDes) {
        super();
        this.retCd = retCd;
        this.msgDes = msgDes;
    }

    /**
     * 【名称】</br>
     * Aesエラーコード取得</br>
     * 
     * @return
     */
    public String getRetCd() {
        return retCd;
    }

    /**
     * 【名称】</br>
     * AesException取得</br>
     * 
     * @return
     */
    public Exception getException() {
        return exception;
    }

    /**
     * 【名称】</br>
     * AesMessage取得</br>
     * 
     * @return
     */
    public String getMsgDes() {
        return msgDes;
    }
}
