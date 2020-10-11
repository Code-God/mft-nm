package com.meifute.nm.others.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Classname Qrcode
 * @Description
 * @Date 2020-07-07 12:00
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class QrCode {

    // 宽度
    private static final int LOGO_WIDTH = 500;
    // 高度
    private static final int LOGO_HEIGHT = 500;
    // 格式
    private static final String FORMAT = "jpg";

    public String getQrCode(String text, AliyunOss oss) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, LOGO_WIDTH, LOGO_HEIGHT);
            MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, os);
            byte[] bytes = os.toByteArray();
            return oss.ossUploadByteStream(UUID.randomUUID().toString()+"."+FORMAT, bytes);
        } catch (WriterException | IOException e) {
            log.error("生成二维码异常:{0}", e);
            return null;
        }
    }
}
