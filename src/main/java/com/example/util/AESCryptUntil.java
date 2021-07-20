package com.example.util;

import com.example.constant.Constants;
import com.example.exception.AesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


/**
 * 【名称】</br>
 * 暗号化・復号メッソド
 *
 * @author eptsz01
 */

@Component
public class AESCryptUntil {

    private final Logger logger = LoggerFactory.getLogger(AESCryptUntil.class);

    /**
     * 【名称】</br>
     * AESのECBモードで暗号化する 不可変の暗号化文を提供する
     *
     * @param plaintext 平文
     * @return 暗号化文
     */
    public String queryableEncrypt(String plaintext) {
        // パラメータはNullの場合、そのまま返します。
        if (!StringUtils.hasLength(plaintext)) {
            return plaintext;
        }
        // 暗号化キーを取得
        String key = Constants.SECREAT_KEY;

        // 暗号化結果Byte
        byte[] crypted = null;

        try {
            // 暗号化モデルを設定AES/ECB/PKCS5Padding
            SecretKeySpec skey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(key), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);

            crypted = cipher.doFinal(plaintext.getBytes("shift-jis"));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | IllegalArgumentException | UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);

            throw new AesException(e);
        }

        return DatatypeConverter.printBase64Binary(crypted);
    }

    /**
     * 【名称】</br>
     * AESのECBで復号 不可変の暗号化文を復号
     *
     * @param ciphertext 暗号化文
     * @return 平文
     */
    public String queryableDecrypt(String ciphertext) {
        // パラメータはNullの場合、そのまま返します。
        if (!StringUtils.hasLength(ciphertext)) {
            return ciphertext;
        }
        // 暗号化キーを取得
        String key = Constants.SECREAT_KEY;
        String strOut = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(key), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            byte[] output = cipher.doFinal(DatatypeConverter.parseBase64Binary(ciphertext));
            strOut = new String(output, "shift-jis");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | IllegalArgumentException | UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new AesException(e);
        }

        return strOut;
    }

    /**
     * 【名称】</br>
     * AESのCBCモードで暗号化する 可変の暗号化文を提供する
     *
     * @param plaintext 平文
     * @return 暗号化文
     */
    public String textEncrypt(String plaintext) {

        // パラメータはNullの場合、そのまま返します。
        if (!StringUtils.hasLength(plaintext)) {
            return plaintext;
        }

        String strResult = null;
        // 初期化Victory
        String S_IV = getRandomString(16);
        String randomString = getRandomString(16);
        String targetString = randomString + Constants.KANMA + plaintext;
        String S_KEY = Constants.SECREAT_KEY;
        try {
            // 文字列をバイト配列へ変換
            byte[] byteText = targetString.getBytes("shift-jis");

            // 暗号化キーと初期化ベクトルをバイト配列へ変換
            byte[] byteKey = DatatypeConverter.parseBase64Binary(S_KEY);
            byte[] byteIv = S_IV.getBytes();

            // 暗号化キーと初期化ベクトルのオブジェクト生成
            SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(byteIv);

            // Cipherオブジェクト生成
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Cipherオブジェクトの初期化
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            // 暗号化の結果格納
            byte[] byteResult = cipher.doFinal(byteText);

            // Base64へエンコード
            strResult = DatatypeConverter.printBase64Binary(byteResult);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new AesException(e);
        }

        return strResult;

    }

    /**
     * 【名称】</br>
     * AESのCBCで復号 可変の暗号化文を復号
     *
     * @param ciphertext 暗号化文
     * @return 平文
     */
    public String textDecrypt(String ciphertext) {
        // パラメータはNullの場合、そのまま返します。
        if (!StringUtils.hasLength(ciphertext)) {
            return ciphertext;
        }

        String strResult = null;
        String strResultCut = null;
        try {
            String S_IV = getRandomString(16);
            String S_KEY = Constants.SECREAT_KEY;
            // Base64をデコード
            byte[] byteText = DatatypeConverter.parseBase64Binary(ciphertext);

            // 暗号化キーと初期化ベクトルをバイト配列へ変換
            byte[] byteKey = DatatypeConverter.parseBase64Binary(S_KEY);
            byte[] byteIv = S_IV.getBytes();

            // 復号化キーと初期化ベクトルのオブジェクト生成
            SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(byteIv);

            // Cipherオブジェクト生成
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Cipherオブジェクトの初期化
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            // 復号化の結果格納
            byte[] byteResult = cipher.doFinal(byteText);

            // バイト配列を文字列へ変換
            strResult = new String(byteResult, "shift-jis");

            // 前の16桁を削除します。
            strResultCut = strResult.substring(17);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new AesException(e);
        }
        // 復号化文字列を返却
        return strResultCut;
    }

    /**
     * 【名称】</br>
     * AESのCBCモードで暗号化する 可変の暗号化文を提供する Qrデータ専用
     *
     * @param plaintext 平文
     * @return 暗号化文
     */
//    public String textEncryptQrData(String plaintext) {
//
//        // パラメータはNullの場合、そのまま返します。
//        if (StringUtils.hasLength(plaintext)) {
//            return plaintext;
//        }
//
//        String strResult = null;
//        // 初期化Victory
//        String S_IV = getRandomString(16);
//        String randomString = getRandomString(16);
//        String targetString = randomString + Constants.KANMA + plaintext;
//        String S_KEY = Constants.SECREAT_QRDATA_KEY;
//        try {
//            // 文字列をバイト配列へ変換
//            byte[] byteText = targetString.getBytes("shift-jis");
//
//            // 暗号化キーと初期化ベクトルをバイト配列へ変換
//            byte[] byteKey = DatatypeConverter.parseBase64Binary(S_KEY);
//            byte[] byteIv = S_IV.getBytes();
//
//            // 暗号化キーと初期化ベクトルのオブジェクト生成
//            SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
//            IvParameterSpec iv = new IvParameterSpec(byteIv);
//
//            // Cipherオブジェクト生成
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//
//            // Cipherオブジェクトの初期化
//            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//
//            // 暗号化の結果格納
//            byte[] byteResult = cipher.doFinal(byteText);
//
//            // Base64へエンコード
//            strResult = DatatypeConverter.printBase64Binary(byteResult);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
//            throw new AesException(e);
//        }
//
//        return strResult;
//
//    }

    /**
     * 【名称】</br>
     * Random数を取得
     *
     * @param length Random数のLength
     * @return 指定長さのランダム文字列
     */
    public String getRandomString(int length) {

        StringBuilder randomBuilder = new StringBuilder();
        Random rd = new SecureRandom();
        for (int i = 0; i < length; i++) {
            // 0-2のランダム数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    // 0-9的ランダム
                    randomBuilder.append(rd.nextInt(10));
                    break;
                case 1:
                    // ASCIIは65-90のランダム大文字英語
                    randomBuilder.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    // ASCIIは97-122ランダム小文字英語
                    randomBuilder.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }

        return randomBuilder.toString();
    }

}
