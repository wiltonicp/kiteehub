package com.kiteehub.common.util;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 加密工具类
 *
 * @author Ranger
 * @date 2024/3/9
 **/
public class CommonEncrypt {

    public static final String SM2_PUBLICKKEY = "";
    public static final String SM2_PRIVATEKEY = "";
    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    private static ECParameterSpec ecDomainParameters;

    public CommonEncrypt() {
    }

    public static KeyPair createECKeyPair() {
        ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
            kpg.initialize(sm2Spec, new SecureRandom());
            return kpg.generateKeyPair();
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String publicKeyHex, String data) {
        return encrypt(getECPublicKeyByPublicKeyHex(publicKeyHex), data, 1);
    }

    public static String encrypt(BCECPublicKey publicKey, String data, int modeType) {
        SM2Engine.Mode mode = SM2Engine.Mode.C1C3C2;
        if (modeType != 1) {
            mode = SM2Engine.Mode.C1C2C3;
        }

        ECParameterSpec ecParameterSpec = publicKey.getParameters();
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(), ecParameterSpec.getG(), ecParameterSpec.getN());
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(publicKey.getQ(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine(mode);
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        byte[] arrayOfBytes = null;

        try {
            byte[] in = data.getBytes("utf-8");
            arrayOfBytes = sm2Engine.processBlock(in, 0, in.length);
        } catch (Exception var10) {
            System.out.println("SM2加密时出现异常:" + var10.getMessage());
            var10.printStackTrace();
        }

        return Hex.toHexString(arrayOfBytes);
    }

    public static String decrypt(String privateKeyHex, String cipherData) {
        return decrypt(getBCECPrivateKeyByPrivateKeyHex(privateKeyHex), cipherData, 1);
    }

    public static String decrypts(String privateKeyHex, String cipherData) {
        int x = 12;
        int y = 22;
        int z = 30;
        Date date = new Date();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        long endMillis = startCalendar.getTimeInMillis();
        int index = cipherData.indexOf("DateValidIdentify");
        if (index > 0) {
            String data = cipherData.substring(0, index);
            String DateMW = decrypt(privateKeyHex, cipherData.substring(index + 17, cipherData.length()));
            long diffM = Math.abs(endMillis - Long.parseLong(DateMW)) / 60000L;
            if (diffM <= 15L && diffM >= 0L) {
                data = data.substring(0, x) + data.substring(x + 2, y + 2) + data.substring(y + 4, z + 4) + data.substring(z + 6, data.length());
                return decrypt(getBCECPrivateKeyByPrivateKeyHex(privateKeyHex), data, 1);
            } else {
                return "outDate";
            }
        } else {
            return "invalid";
        }
    }

    public static String decrypt(BCECPrivateKey privateKey, String cipherData, int modeType) {
        SM2Engine.Mode mode = SM2Engine.Mode.C1C3C2;
        if (modeType != 1) {
            mode = SM2Engine.Mode.C1C2C3;
        }

        byte[] cipherDataByte = Hex.decode(cipherData);
        ECParameterSpec ecParameterSpec = privateKey.getParameters();
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(), ecParameterSpec.getG(), ecParameterSpec.getN());
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(privateKey.getD(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine(mode);
        sm2Engine.init(false, ecPrivateKeyParameters);
        String result = null;

        try {
            byte[] arrayOfBytes = sm2Engine.processBlock(cipherDataByte, 0, cipherDataByte.length);
            result = new String(arrayOfBytes, "utf-8");
        } catch (Exception var11) {
            System.out.println("SM2解密时出现异常" + var11.getMessage());
        }

        return result;
    }

    public static BCECPublicKey getECPublicKeyByPublicKeyHex(String pubKeyHex) {
        if (pubKeyHex.length() > 128) {
            pubKeyHex = pubKeyHex.substring(pubKeyHex.length() - 128);
        }

        String stringX = pubKeyHex.substring(0, 64);
        String stringY = pubKeyHex.substring(stringX.length());
        BigInteger x = new BigInteger(stringX, 16);
        BigInteger y = new BigInteger(stringY, 16);
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecDomainParameters);
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    public static BCECPrivateKey getBCECPrivateKeyByPrivateKeyHex(String privateKeyHex) {
        BigInteger d = new BigInteger(privateKeyHex, 16);
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecDomainParameters);
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    public static String mixMw(String mw) {
        int t1 = 12;
        int t2 = 22;
        int t3 = 30;
        UUID randomUUid = UUID.randomUUID();
        String hxStr = randomUUid.toString().replaceAll("-", "");
        String s1 = hxStr.substring(1, 3);
        String s2 = hxStr.substring(3, 5);
        String s3 = hxStr.substring(8, 10);
        mw = mw.substring(0, t1) + s1 + mw.substring(t1, t2) + s2 + mw.substring(t2, t3) + s3 + mw.substring(t3, mw.length());
        return mw;
    }

    static {
        ecDomainParameters = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());
    }
}
